package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Admin;
import com.example.teachermanagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;


    public Admin checkAdmin(String username, String password) {
        return adminRepository.findByNameAndPassword(username, password);
    }
    public Admin saveAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setName(username);
        admin.setPassword(password);
        return adminRepository.save(admin);
    }


}
