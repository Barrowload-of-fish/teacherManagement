package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/teacher")
    public String getTeacherByName(@RequestParam String name, Model model) throws IOException {
        Teacher teacher = teacherService.getAndSaveTeacherInfo(name);
        model.addAttribute("teacher", teacher);
        return "teacherInfo";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }
}
