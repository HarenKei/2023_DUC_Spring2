package com.example.ch05.service;

import com.example.ch05.domain.User;
import com.example.ch05.dto.AddUserRequest;
import com.example.ch05.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest req) {
        User newUser = User.builder()
                .email(req.getEmail())
                .password(bCryptPasswordEncoder.encode(req.getPassword())) // 패스워드 암호화
                .build();
        return userRepository.save(newUser).getId();
    }

}