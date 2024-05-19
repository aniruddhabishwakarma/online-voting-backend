package com.project.System.service.vote;

import com.project.System.entity.CandidateEntity;
import com.project.System.entity.EventsEntity;
import com.project.System.entity.UserEntity;
import com.project.System.entity.VoteEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.Results;
import com.project.System.model.Vote;
import com.project.System.repository.VoteRepository;
import com.project.System.service.UserService;
import com.project.System.service.candidate.CandidateService;
import com.project.System.service.events.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImplementation implements VoteService{

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private UserService userService;
    @Override
    public ResponseEntity<?> registerVoter(Vote vote) {

        EventsEntity eventsEntity = eventsService.findById(vote.getEventId());
        UserEntity userEntity = userService.findById(vote.getVoterId());
        CandidateEntity candidateEntity = candidateService.findById(vote.getCandidateId());

        VoteEntity voteEntity = VoteEntity.builder()
                .event(eventsEntity)
                .user(userEntity)
                .candidate(candidateEntity)
                .build();
        voteRepository.save(voteEntity);
        return new ResponseEntity<>(new CustomMessage("Vote has been submitted succesfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getResults(Integer eventId) {
        System.out.println(eventId);
        List<Results>  result = voteRepository.getResults(eventId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
