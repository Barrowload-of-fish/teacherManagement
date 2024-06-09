package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Project;
import com.example.teachermanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectController {
    // Controller code goes here
    @Autowired
    private ProjectService projectService;


    @GetMapping("/projects")
    public String projects(Model model) {
        List<Project> projects = projectService.findAll();
        return "projects";
    }
    @PostMapping("/project")
    public String project(Model model, String name) {
        Project project = projectService.findByProjectname(name);
        if (project == null) {
            model.addAttribute("projectNotFound", true);
        } else {
            model.addAttribute("project", project);
        }
        return "project";
    }

    @PostMapping("/projectsof")
    public String projectof(Model model,String teacherName) {
        List<Project> projects = projectService.findByteacher(teacherName);
        if(projects.isEmpty()) {
            model.addAttribute("message","No projects found for this teacher");
        }
        else {
            model.addAttribute("projects",projects);
            model.addAttribute("teacherName",teacherName);
        }
        return "projectsof";
    }
    @GetMapping("/addproject")
    public String addProject(Model model) {
        return "addproject";
    }

    @PostMapping("/addproject")
    public String addProject(Model model,Project project) {
        projectService.save(project);
        return "projects";
    }

}
