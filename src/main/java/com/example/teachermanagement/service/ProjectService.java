package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Project;
import com.example.teachermanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    public List<Project> findByteacher(String name) {
        return projectRepository.findByLeader(name);
    }
    public Project findByProjectname(String project_name) {
        return projectRepository.findByName(project_name);
    }
    public void save(Project project) {
        projectRepository.save(project);
    }

}
