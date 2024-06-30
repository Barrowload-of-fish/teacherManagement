package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Admin;
import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.repository.AdminRepository;
import com.example.teachermanagement.repository.LoginRepository;
import com.example.teachermanagement.repository.StudentRepository;
import com.example.teachermanagement.repository.TeacherRepository;
import com.example.teachermanagement.service.AdminService;
import com.example.teachermanagement.service.LoginService;
import com.example.teachermanagement.service.StudentService;
import com.example.teachermanagement.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @GetMapping("/admin_list")
    public String adminList(Model model) {
        model.addAttribute("admins", adminRepository.findAll());
        return "admin_list";
    }
    @GetMapping("/admin_login")
    public String login() {
        return "admin_login";
    }

    @PostMapping("/admin_login")
    public String adminLogin(Model model, @RequestParam ("name")String name, @RequestParam ("password")String password) {
        Admin admin = adminService.checkAdmin(name, password);
        if(admin!=null){
            model.addAttribute("admin", admin);
            return "welcome_admin";
        }else{
            model.addAttribute("error", "Invalid username or password");
            return "admin_login";
        }
    }
    @GetMapping("/add_admin")
    public String addAdmin(Model model) {
        return "add_admin";
    }
    @PostMapping("/add_admin")
    public String addAdmin(Model model, @RequestParam("name") String name, @RequestParam("password") String password) {
        Admin admin = new Admin();
        admin.setName(name);
        System.out.println("add_admin/passwprd:"+password);
        admin.setPassword(password);
        adminRepository.save(admin);
        return "redirect:/admin_list";
    }

    @GetMapping("/teacher_list")
    public String  teacherList(Model model){
        model.addAttribute("teachers",loginRepository.findAll());
        return "teacher_list";
    }
    @GetMapping("/student_list")
    public String  studentList(Model model){
        model.addAttribute("students",studentRepository.findAll());
        return "student_list";
    }
    @PostMapping("/reset/teacher/{email}")
    public String teacherReset(Model model, @PathVariable String email) {
        Login teacher = loginService.findByEmail(email);
        if (teacher != null) {
            System.out.println("teacher:" + teacher);
            String password = "111111";
            teacher.setPassword(password);
            // 保存更改到数据库
            loginService.saveLogin(teacher);
            model.addAttribute("msg", "修改成功");
        }
        return "redirect:/teacher_list";
    }
    @PostMapping("/reset/student/{suid}")
    public String studentReset(Model model, @PathVariable String suid) {
        Student student=studentRepository.findBySuid(suid);
        if (student != null) {
            System.out.println("student:" + student);
            String password = "111111";
            student.setPassword(password);
            // 保存更改到数据库
            studentRepository.save(student);
            model.addAttribute("msg", "修改成功");
        }
        return "redirect:/student_list";
    }
}
