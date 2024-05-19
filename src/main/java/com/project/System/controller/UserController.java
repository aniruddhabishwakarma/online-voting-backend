package com.project.System.controller;

import com.project.System.config.JwtTokenUtil;
import com.project.System.model.AuthRequest;
import com.project.System.model.RegisterUser;
import com.project.System.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;



    private final JwtTokenUtil jwtTokenUtil;


//    @PostMapping("/register")
//    public String registerUser(@RequestBody UserEntity user){
//        try{
//            System.out.println(user);
//            return userService.registerUser(user);
//        }catch(Exception e){
//            e.printStackTrace();
//            return "Error register" + e.getMessage();
//        }
//
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){

        return userService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUser registerUser) {
        return userService.register(registerUser);
    }

    @GetMapping("/message")
    public String message(){
        return "Hello World";
    }


}
