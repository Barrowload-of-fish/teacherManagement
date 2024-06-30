package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Project;
import com.example.teachermanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ProjectService {
    // TODO: Implement project service
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    public Project findByName(String name) {return projectRepository.findByName(name);}
    public List<Project> findByLeader(String leader) {
        System.out.println("service: " + leader);
        return projectRepository.findByLeader(leader);
    }
    public Project save(Project project) {return projectRepository.save(project);}

    public Project findById(int id) {
        return projectRepository.findById(id);
    }
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
