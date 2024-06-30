package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Project;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.service.ProjectService;
import com.example.teachermanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TeacherService teacherService;
    private Teacher teacher;

    @GetMapping("/projects")
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/addproject")
    public String addProject(Model model) {
        model.addAttribute("name", teacher.getName());
        return "addproject";
    }

    @PostMapping("/addproject")
    public String addProjectSubmit(@ModelAttribute Project project, Model model) {
        projectService.save(project);
        String leader = project.getLeader();
        String encodedLeader = URLEncoder.encode(leader, StandardCharsets.UTF_8);
        System.out.println("Redirecting to: /edit_project/" + leader);
        model.addAttribute("name", teacher.getName());
        return "redirect:/edit_project/" +encodedLeader;
    }
    @GetMapping("/edit/{id}")
    public String editProject(Model model, @PathVariable int id) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editProjectSubmit(@ModelAttribute("project") Project project,@PathVariable int id) {
        Project existingProject = projectService.findById(id);
        if (existingProject != null) {
            existingProject.setName(project.getName());
            existingProject.setLaboratory(project.getLaboratory());
            existingProject.setDescription(project.getDescription());
            existingProject.setLeader(project.getLeader());
            existingProject.setNumberOfStudents(project.getNumberOfStudents());
            existingProject.setNumberOfTeachers(project.getNumberOfTeachers());
            existingProject.setStartDate(project.getStartDate());
            existingProject.setEndDate(project.getEndDate());
            existingProject.setStatus(project.getStatus());
            projectService.save(existingProject);
        }

        String leader = project.getLeader();
        String encodedLeader = URLEncoder.encode(leader, StandardCharsets.UTF_8);
        return "redirect:/edit_project/" + encodedLeader;
    }


    @GetMapping("/edit_project/{leader}")
    public String editProjectSubmit(@PathVariable("leader") String leader, Model model) {
        List<Project> projects = projectService.findByLeader(leader);
        teacher = teacherService.findByName(leader);
        System.out.println("Leader: " + leader);
        System.out.println("Projects: " + projects);
        model.addAttribute("projects", projects);
        model.addAttribute("leader", leader);
        return "edit_project";
    }

}
