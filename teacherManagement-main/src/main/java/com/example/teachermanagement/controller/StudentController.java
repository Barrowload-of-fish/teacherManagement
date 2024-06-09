
package com.example.teachermanagement.controller;


import com.example.teachermanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/slogin")
    public String studentLogin() {
        return "login";

    }

    //学生登陆会提交suid和password参数
    @PostMapping("/slogin")
    public String studentLogin(Model model, @RequestParam("suid") String suid, @RequestParam("password") String password) {
        if (studentService.getStudentBySuidAndPassword(suid, password) != null) {
            return "welcome";
        } else if (studentService.getStudentBySuid(suid) != null) {
            model.addAttribute("error", "密码错误");
            return "slogin";
        } else {
            model.addAttribute("error", "用户名不存在");
            return "sregister";
        }
    }
    //学生注册页面
    @GetMapping("/sregister")
    public String studentRegister() {
        return "sregister";
    }
    //学生注册提交suid，name,password和confirmPassword参数
    @PostMapping("/sregister")
    public String studentRegister(Model model, @RequestParam("suid") String suid, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        if (studentService.getStudentBySuid(suid) != null) {
            model.addAttribute("error", "用户名已存在");
            return "sregister";
        } else if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "两次密码输入不一致");
            return "sregister";
        } else {
            studentService.saveStudent(suid, name, password);
            return "slogin";
        }
    }
}

