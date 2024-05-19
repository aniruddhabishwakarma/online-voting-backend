package com.project.System.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.System.entity.UserEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.Candidate;
import com.project.System.service.candidate.CandidateService;
import com.project.System.service.events.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CandidateController {
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${project.image}")
    private String path;

    @Autowired
    private CandidateService candidateService;



    @PostMapping("/user/candidate/register")
    public ResponseEntity<?> registerCandidate(@RequestParam("data") String data,
                                               @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Candidate candidate = objectMapper.readValue(data, Candidate.class);
        if(!authentication.getName().equals(candidate.getUsername())){
            return new ResponseEntity<>(new CustomMessage("You cannot perform this action"), HttpStatus.UNAUTHORIZED);
        }
        return candidateService.registerCandidate(candidate,file);
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<?> viewCandidate(@PathVariable("id") int id){
//
//        return candidateService.viewCandidates(id);
//    }

    @GetMapping("/admin/candidates")
    public ResponseEntity<?> getUnApprovedCandidates() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        return candidateService.getCandidates();
    }

    @GetMapping("/users/candidates/photo")
    public ResponseEntity<?> displayPhoto(@RequestParam("fileName") String fileName){
        return candidateService.returnPhoto(fileName);
    }
    @GetMapping("/admin/candidates/count")
    public int getCandidateCount(){
        return candidateService.candidateCount();

    }
    @GetMapping("/users/candidates/{id}")
    public ResponseEntity<?> approvedCandidates(@PathVariable int id){
        System.out.println(id);
        System.out.println("Hit  Chaak Api");
        return candidateService.approvedCandidate(id);

    }
    @PutMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveCandidate(@PathVariable int id){
        
        return candidateService.approveCandidate(id);

    }
}
