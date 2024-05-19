package com.project.System.controller;

import com.project.System.model.Vote;
import com.project.System.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping("/user/vote")
    public ResponseEntity<?> registerVote(@RequestBody Vote vote){

        return voteService.registerVoter(vote);
    }
    @GetMapping("/users/vote/results/{eventId}")
    public ResponseEntity<?> giveResults(@PathVariable("eventId") Integer eventId){
        System.out.println(eventId);
        return voteService.getResults(eventId);
    }

}
