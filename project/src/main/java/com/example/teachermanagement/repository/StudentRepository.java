
package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findBySuid(String suid);

    Student findBySuidAndPassword(String suid, String password);
}