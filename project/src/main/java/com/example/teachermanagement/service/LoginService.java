package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Login findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }


    public Login saveLogin(Login login) {
        return loginRepository.save(login);
    }

    public Login getteacherBySuidAndPassword(String email, String password) {
        return loginRepository.findByemailAndPassword(email, password);
    }
    public boolean emailExists(String email) {
        return loginRepository.findByEmail(email) != null;
    }
    @Transactional
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Login login = loginRepository.findFirstByEmailAndPassword(email, oldPassword);
        if (login != null) {
            login.setPassword(newPassword);
            loginRepository.save(login);
            return true;
        }
        return false;
    }
}
