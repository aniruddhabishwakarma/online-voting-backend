package com.project.System.controller;

import com.project.System.entity.UserEntity;
import com.project.System.model.User;
import com.project.System.service.voters.VotersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VotersController {
    @Autowired
    private VotersService votersService;

    @GetMapping("/user/info")
    public ResponseEntity<User> getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        return votersService.getInfo(authentication.getName());
    }
}
