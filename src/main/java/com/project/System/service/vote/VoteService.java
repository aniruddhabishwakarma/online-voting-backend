package com.project.System.service.vote;

import com.project.System.model.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {
    ResponseEntity<?> registerVoter(Vote vote);

    ResponseEntity<?> getResults(Integer id);
}
