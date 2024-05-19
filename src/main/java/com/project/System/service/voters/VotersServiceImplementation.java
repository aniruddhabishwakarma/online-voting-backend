package com.project.System.service.voters;


import com.project.System.entity.UserEntity;
import com.project.System.model.User;
import com.project.System.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VotersServiceImplementation implements VotersService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public ResponseEntity<User> getInfo(String name) {

        UserEntity user = usersRepository.findByUsername(name).get();
        User userInfo = new User();
        BeanUtils.copyProperties(user,userInfo);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
