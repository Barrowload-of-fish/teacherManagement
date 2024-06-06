package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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




    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public Teacher saveTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

//    @GetMapping("/appointments")
//    public String showAppointmentForm() {
//
//        return "redirect:/appointments?data={id}";
//    }

    @GetMapping("/teacherInfo")
    public String getPage2(Model model) {
        // 从URL参数中获取数据，并添加到模型中
        model.addAttribute("id", "id");
        return "appointments"; // 返回第二个页面
    }


}
