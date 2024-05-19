package com.project.System.service.voters;

import com.project.System.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VotersService {
    ResponseEntity<User> getInfo(String name);
}
