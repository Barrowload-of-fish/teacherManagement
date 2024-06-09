
package com.example.teachermanagement.controller;
import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


import com.example.teachermanagement.model.Teacher;

import com.example.teachermanagement.service.TeacherService;
@Controller
@RequestMapping("/appointments/{username}")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String getAllAppointments(@PathVariable("username") String username, Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("username", username);


        model.addAttribute("appointments", appointments);
        model.addAttribute("teachers", teachers);
     return "appointments"; // returns the appointments.html template
//        return "redirect:/appointments/{username}";
    }

    @PostMapping("/save")
    public String saveAppointment(@RequestParam String studentName,
                                  @RequestParam String appointmentTime_start,
                                  @RequestParam String appointmentTime_end,
                                  @RequestParam Long teacherId) {
        String username=studentName;
        List<Appointment> appointments_old = appointmentService.getAllAppointments();
        for (Appointment appointment : appointments_old) {
            if (appointment.getTeacher().getId().equals(teacherId)) {
                LocalDateTime dateTime_new1 =LocalDateTime.parse(appointmentTime_start);
                LocalDateTime dateTime_new2 =LocalDateTime.parse(appointmentTime_end);
                LocalDateTime dateTime_old1 = appointment.getAppointmentTime_start();
                LocalDateTime dateTime_old2 =appointment.getAppointmentTime_end();

                int comparison1 = dateTime_new1.compareTo(dateTime_old2);
                int comparison2 = dateTime_new2.compareTo(dateTime_old1);
                int comparison_start_end = dateTime_new1.compareTo(dateTime_new2);

                if((!(comparison1>0||comparison2<0))||comparison_start_end>0) {
                    System.out.println("yes");
//                    return "redirect:/appointments";
                  //  return "redirect:/appointments/{username}";
                    return "redirect:/appointments/" + username;
                }
            }
        }

        Appointment appointment = new Appointment();

        appointment.setStudentName(studentName);

        appointment.setAppointmentTime_start(LocalDateTime.parse(appointmentTime_start));

        appointment.setAppointmentTime_end(LocalDateTime.parse(appointmentTime_end));

        Teacher teacher = teacherService.getTeacherById(teacherId);

        appointment.setTeacher(teacher);
        System.out.println("____________");System.out.println("____________");
        System.out.println(appointment.getStudentName());
        System.out.println("____________");

        appointmentService.saveAppointment(appointment);
//        return "redirect:/appointments";
     //   return "redirect:/appointments/{username}";
        return "redirect:/appointments/" + username;
    }



    @PostMapping("/delete")
    public String deleteAppointment(@PathVariable("username") String username,@RequestParam Long id) {
        appointmentService.deleteAppointment(id); // 根据ID删除预约
//        return "redirect:/appointments"; // 重定向到预约列表页面
   return "redirect:/appointments/{username}";

//        return "redirect:/appointments/" + username;
    }











}


