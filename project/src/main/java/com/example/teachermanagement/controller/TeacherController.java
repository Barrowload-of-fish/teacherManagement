package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.repository.AppointmentRepository;
import com.example.teachermanagement.service.TeacherService;
import com.example.teachermanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    private Teacher tea;
    @Autowired
    private LoginService loginService;

    @Autowired
    private AppointmentRepository appointmentService;

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
        System.out.println(name);
        System.out.println("_______________");
        Teacher teachers = teacherService.findByName(name);
        System.out.println(teachers);
        model.addAttribute("teacher", teachers);
        System.out.println(teachers.toString());
        return "teacherInfo";
    }

    @GetMapping("/teacher-info/{id}")
    public String getTeacherInfoById(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "teacherInfo"; // 返回教师基本信息页面
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

    @GetMapping("/recommend-teachers")
    public String recommendTeachers(@RequestParam(required = false) String researchDirection,
                                    @RequestParam(required = false) String college,
                                    @RequestParam(required = false) String title, Model model) {
        List<Teacher> recommendedTeachers = teacherService.recommendTeachers(researchDirection, college, title);
        model.addAttribute("recommendedTeachers", recommendedTeachers);
        model.addAttribute("colleges", teacherService.getAllColleges());
        model.addAttribute("titles", teacherService.getAllTitles());
        return "recommend-teachers"; // 返回教师推荐页面
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
        if (loginService.emailExists(email)) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        try {
            Teacher teacher = teacherService.getAndSaveTeacherInfo(chineseName, email, password);
            if (loginService.getteacherBySuidAndPassword(email, password) == null) {

                Login login = new Login();
                login.setEmail(email);
                login.setPassword(password);
                loginService.saveLogin(login);
                model.addAttribute("message", "Registration successful");

            }
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
    public String loginTeacher(RedirectAttributes redirectAttributes, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        System.out.println("123123123");
        System.out.println(email);

        if (loginService.getteacherBySuidAndPassword(email, password) != null) {
            System.out.println("123123123");
            Teacher teacher =teacherService.findByEmail_(email);
            System.out.println("123123123");
            redirectAttributes.addFlashAttribute("teacher", teacher);
            model.addAttribute("teacher",teacher);
            tea = teacher;
            //redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/welcome_teacher";
            //return "welcome_teacher";


        }
//        else if (studentService.getStudentBySuid(suid) != null) {
//            model.addAttribute("error", "密码错误");
//            return "slogin";
//        } else {
//            model.addAttribute("error", "用户名不存在");
//            return "sregister";
//        }
        else  {return "login";}

    }

    @GetMapping("/change_password")
    public String showChangePasswordForm( Model model)
    {
        model.addAttribute("teacher",tea);
        return "change_password";
    }

    @PostMapping("/change_password")
    public String changePassword( @RequestParam String oldPassword, @RequestParam String newPassword, Model model) {
        boolean success = loginService.changePassword(tea.getEmail(), oldPassword, newPassword);
        if (success) {
            model.addAttribute("teacher",tea);
            model.addAttribute("message", "Password changed successfully");
            return "login";
        } else {
            model.addAttribute("teacher",tea);
            model.addAttribute("error", "Old password is incorrect");
            return "change_password";
        }
    }

    @GetMapping("/welcome_teacher")
    public String welcome(@ModelAttribute("teacher") Teacher teacher, Model model,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("teacher", teacher);
        model.addAttribute("teacher", teacher);
        return "welcome_teacher"; // 返回欢迎页面，例如welcome.html

    }
    @GetMapping("/teacher_message")
    public String teacherMessage(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teacher_message"; // 返回欢迎页面，例如welcome.html
    }
    @GetMapping("/teacherday")
    public String getTeacherAppointments(Model model) {
        Teacher teacher = teacherService.findById(tea.getId());

        List<Appointment> appointments = appointmentService.findByTeacherId(tea.getId());

        model.addAttribute("teacher", teacher);
        model.addAttribute("appointments", appointments);

        return "teacher_appointments";
    }
}
