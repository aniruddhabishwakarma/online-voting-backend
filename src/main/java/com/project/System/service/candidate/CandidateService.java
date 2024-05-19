package com.project.System.service.candidate;

import com.project.System.entity.CandidateEntity;
import com.project.System.model.Candidate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CandidateService {
    ResponseEntity<?> registerCandidate(Candidate candidate, MultipartFile file);
//
//    ResponseEntity<?> viewCandidates(int id);

    ResponseEntity<?> getCandidates();

    ResponseEntity<?> returnPhoto(String fileName);

    ResponseEntity<?> approveCandidate(int id);

    ResponseEntity<?> approvedCandidate(int id);

    int candidateCount();

    CandidateEntity findById(int candidateId);
}
