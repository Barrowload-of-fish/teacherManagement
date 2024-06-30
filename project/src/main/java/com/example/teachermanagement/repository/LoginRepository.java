package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
  Login findByEmail(String email);

  Login findByemailAndPassword(String email, String password);

  Login findFirstByEmailAndPassword(String email, String oldPassword);
}
