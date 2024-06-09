package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Optional<Login> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    public Login saveLogin(Login login) {
        return loginRepository.save(login);
    }
}
