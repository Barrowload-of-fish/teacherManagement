
package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findBySuid(String suid);
    List<Student> findAll();
    Student findBySuidAndPassword(String suid, String password);
}