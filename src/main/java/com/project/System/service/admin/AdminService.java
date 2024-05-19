package com.project.System.service.admin;

import com.project.System.model.Password;
import com.project.System.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    ResponseEntity<User> getData(String username);

    ResponseEntity<?> updateData(User user, String username);

    ResponseEntity<?> updatePassword(Password password, String name);
}
