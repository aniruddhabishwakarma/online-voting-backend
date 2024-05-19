package com.project.System.service;

import com.project.System.config.JwtResponse;
import com.project.System.config.JwtTokenUtil;
import com.project.System.config.PasswordEncoder;
import com.project.System.entity.UserEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.AuthRequest;
import com.project.System.model.RegisterUser;
import com.project.System.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableAsync
public class UserService implements  UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username:"+username);
        }
        return user.get();
    }

    public ResponseEntity<?> register(RegisterUser user) {

        UserEntity userEntity = UserEntity.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .contact(user.getContact())
                .email(user.getEmail())
                .password(passwordEncoder.encodePassword(user.getPassword()))
                .roles(user.getRole())
                .build();

        usersRepository.save(userEntity);
        return ResponseEntity.ok(new CustomMessage("User registered successfully"));
    }

    public ResponseEntity<?> login(AuthRequest authRequest) {
        Optional<UserEntity> user = usersRepository.findByUsername(authRequest.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomMessage("Username Error"));
        }
        if (passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.badRequest().body(new CustomMessage("Password Error"));
        }
        return ResponseEntity.ok(generateToken(user.get()));
    }
    private JwtResponse generateToken(UserEntity userDetails) {
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }
    private String hashPassword(String password) {
        return new PasswordEncoder().encodePassword(password);
    }

    public UserEntity findById(int userId) {
        return usersRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }
}
