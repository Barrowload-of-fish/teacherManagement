package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    List<Teacher> findByName(String name);
}
