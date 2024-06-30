package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // TODO: Implement methods for admin repository
    Admin findAdminById(Long id);
    Admin findByName(String name);
    Admin findByNameAndPassword(String email, String password);
    Admin save(Admin admin);
    List<Admin> findAll();
    Admin deleteAdminById(Long id);
}
