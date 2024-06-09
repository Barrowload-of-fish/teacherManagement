
package com.example.teachermanagement.controller;


import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.service.AppointmentService;
import com.example.teachermanagement.service.StudentService;
import com.example.teachermanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/slogin")
    public String studentLogin() {
        return "slogin";

    }

    //学生登陆会提交suid和password参数
    @PostMapping("/slogin")
    public String studentLogin(RedirectAttributes redirectAttributes, Model model, @RequestParam("suid") String suid, @RequestParam("password") String password) {
        if (studentService.getStudentBySuidAndPassword(suid, password) != null) {
            System.out.println(suid);
            Student student =studentService.getStudentBySuid(suid);
            redirectAttributes.addFlashAttribute("student", student);
            model.addAttribute("student",student);
            //redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/welcome";
        } else if (studentService.getStudentBySuid(suid) != null) {
            model.addAttribute("error", "密码错误");
            return "slogin";
        } else {
            model.addAttribute("error", "用户名不存在");
            return "sregister";
        }
    }

    @GetMapping("/welcome")
    public String welcome(@ModelAttribute("student") Student student, Model model,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("student", student);
        model.addAttribute("student", student);
        model.addAttribute("student", student);
        return "welcome"; // 返回欢迎页面，例如welcome.html

    }








    @GetMapping("/teacher_day")
    public String showTeacherSelection(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teacher_selection";
    }

    @GetMapping("/teacher_day/{id}")
    public String getTeacherAppointments(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<Appointment> appointments = appointmentService.findByTeacherId(id);

        model.addAttribute("teacher", teacher);
        model.addAttribute("appointments", appointments);

        return "teacher_appointments";
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

