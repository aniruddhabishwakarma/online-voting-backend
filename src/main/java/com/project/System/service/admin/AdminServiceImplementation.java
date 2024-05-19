package com.project.System.service.admin;

import com.project.System.config.PasswordEncoder;
import com.project.System.entity.UserEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.Password;
import com.project.System.model.User;
import com.project.System.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImplementation implements AdminService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<User> getData(String username) {

        UserEntity user = usersRepository.findByUsername(username).get();
        User userInfo = new User();
        BeanUtils.copyProperties(user,userInfo);

        return new ResponseEntity<>(userInfo,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateData(User user,String username) {

        UserEntity userEntity = usersRepository.findByUsername(username).get();
        userEntity.setFullName(user.getFullName());
        userEntity.setUsername(user.getUsername());
        userEntity.setContact(user.getContact());
        userEntity.setEmail(user.getEmail());
        usersRepository.save(userEntity);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePassword(Password password, String name) {

        UserEntity userEntity = usersRepository.findByUsername(name).get();
        if(passwordEncoder.matches(password.getCurrentPassword(), userEntity.getPassword())){
            return ResponseEntity.badRequest().body(new CustomMessage("Your password does not match"));
        }else{
            userEntity.setPassword(passwordEncoder.encodePassword(password.getNewPassword()));
            usersRepository.save(userEntity);
            return ResponseEntity.ok().body(new CustomMessage("Password Updated Succesfully"));
        }

    }
}
