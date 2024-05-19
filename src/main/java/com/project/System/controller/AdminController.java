package com.project.System.controller;

import com.project.System.entity.UserEntity;
import com.project.System.model.Password;
import com.project.System.model.User;
import com.project.System.service.admin.AdminService;
import com.project.System.service.candidate.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/info")
    public ResponseEntity<User> getData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return adminService.getData(authentication.getName());
    }
    @PutMapping("/updateInfo")
    public ResponseEntity<?> updateData(@RequestBody User user){
        System.out.println(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminService.updateData(user,authentication.getName());
    }
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Password password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminService.updatePassword(password,authentication.getName());
    }


}
