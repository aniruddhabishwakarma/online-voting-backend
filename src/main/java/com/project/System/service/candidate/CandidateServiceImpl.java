package com.project.System.service.candidate;

import com.amazonaws.util.IOUtils;
import com.project.System.entity.CandidateEntity;
import com.project.System.entity.EventsEntity;
import com.project.System.entity.UserEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.Candidate;
import com.project.System.model.CandidateInfo;
import com.project.System.repository.CandidateRepository;
import com.project.System.service.UserService;
import com.project.System.service.events.EventsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private EventsService eventsService;

    @Autowired
    private UserService userService;

    @Value("${project.image}")
    private String path;
    @Override
    public ResponseEntity<?> registerCandidate(Candidate candidate, MultipartFile file) {
        try{
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            int lastDotIndex = originalFileName.lastIndexOf('.');
            if (lastDotIndex != -1) {
                extension = originalFileName.substring(lastDotIndex);
            }

            String fileName = "candidate" + UUID.randomUUID() + extension;
            String filePath = path + File.separator + fileName;

            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
            Files.copy(file.getInputStream(), Paths.get(filePath));

            EventsEntity eventsEntity = eventsService.findById(candidate.getEventId());
            UserEntity userEntity = userService.findById(candidate.getUserId());

            Random random = new Random();

            CandidateEntity candidateEntity = CandidateEntity.builder().
                    event(eventsEntity)
                    .description(candidate.getDescription())
                    .photo("/users/candidates/photo?fileName=" + fileName)
                    .user(userEntity)
                    .status(false)
                    .build();
            candidateRepository.save(candidateEntity);
            return new ResponseEntity<>(new CustomMessage("Candidate has been registered successfully"), HttpStatus.OK);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    @Override
//    public ResponseEntity<?> viewCandidates(int id) {
//
//        return null;
//    }

    @Override
    public ResponseEntity<?> getCandidates() {
        List<CandidateInfo> candidateEntities = candidateRepository.getUnApprovedCandidate();
        System.out.println(candidateEntities);
//        List<CandidateInfo> candidateInfo = candidateEntities.stream()
//                .map(data->{
//                    CandidateInfo details = new CandidateInfo();
//                    details.setId(data.getUser().getId());
//                    details.setFullName(data.getUser().getFullName());
//                    details.setDescription(data.getDescription());
//                    details.setPhoto(data.getPhoto());
//                    return details;
//                }).collect(Collectors.toList());
//

        return ResponseEntity.ok(candidateEntities);
    }



    @Override
    public ResponseEntity<?> returnPhoto(String fileName) {
        String filePath = path + File.separator+fileName;
        Logger.getAnonymousLogger().log(Level.FINE,filePath);
        try{
            InputStream inputStream = new FileInputStream(filePath);
            byte[] out = IOUtils.toByteArray(inputStream);
            HttpHeaders responseHeaders=new HttpHeaders();
            responseHeaders.set("charset","utf-8");
            responseHeaders.setContentType(MediaType.IMAGE_JPEG);
            return  new ResponseEntity<>(out,responseHeaders,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CustomMessage.builder().message(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> approveCandidate(int id) {
        CandidateEntity candidateEntity = candidateRepository.findById(id).get();
        candidateEntity.setStatus(true);
        candidateRepository.save(candidateEntity);
        return ResponseEntity.ok("Approved");
    }

    @Override
    public ResponseEntity<?> approvedCandidate(int id) {
        List<CandidateInfo> candidateInfo = candidateRepository.getApprovedCandidate(id);
        return ResponseEntity.ok(candidateInfo);
    }

    @Override
    public int candidateCount() {
        int count = candidateRepository.findAll().size();
        return count;
    }

    @Override
    public CandidateEntity findById(int candidateId) {
        return candidateRepository.findById(candidateId).
                orElseThrow(()->new EntityNotFoundException("Candidate Not found"));
    }


}
