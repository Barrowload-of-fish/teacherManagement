package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.service.TeacherService;
import com.example.teachermanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/teachers")
    @ResponseBody
    public List<Teacher> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/teacher")
    public String getTeacherByName(@RequestParam String name, Model model) throws IOException {
        System.out.println("________________________");
        System.out.println(name);
        System.out.println("_______________");
        Teacher teachers = teacherService.findByName(name);
        System.out.println(teachers);
        model.addAttribute("teacher", teachers);
        System.out.println(teachers.toString());
        return "teacherInfo";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchTeacherByChineseName(@RequestParam String chineseName, Model model) {
        try {
            Teacher teacher = teacherService.getAndSaveTeacherInfo(chineseName, null, null);
            model.addAttribute("teacher", teacher);
            return "teacherInfo";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to scrape teacher info");
            return "search";
        }
    }

    @GetMapping("/teacher/{id}")
    @ResponseBody
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("/teacher")
    @ResponseBody
    public Teacher saveTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @DeleteMapping("/teacher/{id}")
    @ResponseBody
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerTeacher(@RequestParam String chineseName, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            Teacher teacher = teacherService.getAndSaveTeacherInfo(chineseName, email, password);
            Login login = new Login();
            login.setEmail(email);
            login.setPassword(password);
            loginService.saveLogin(login);
            model.addAttribute("message", "Registration successful");
            return "login";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to scrape teacher info");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginTeacher(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Login> login = loginService.findByEmail(email);
        if (login.isPresent() && login.get().getPassword().equals(password)) {
            Optional<Teacher> teacher = teacherService.findByEmail(email);
            if (teacher.isPresent()) {
                model.addAttribute("teacher", teacher.get());
                return "welcome";
            } else {
                model.addAttribute("error", "Teacher not found");
                return "login";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
