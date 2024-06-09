package com.example.teachermanagement.repository;

import com.example.teachermanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByName(String name);

    List<Project> findByLeader(String teacher);


}
