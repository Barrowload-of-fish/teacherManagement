package com.example.teachermanagement.controller;

import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.service.AppointmentService;
import com.example.teachermanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
        System.out.println("Appointments: " + appointments);
        model.addAttribute("username", username);
        model.addAttribute("appointments", appointments);
        return "appointments"; // returns the appointments.html template
    }

    @GetMapping("/create")
    public String createAppointmentForm(@PathVariable("username") String username, Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("username", username);
        model.addAttribute("teachers", teachers);
        return "createAppointment"; // returns the createAppointment.html template
    }

    @PostMapping("/save")
    public String saveAppointment(@PathVariable("username") String username,
                                  @RequestParam String studentName,
                                  @RequestParam String appointmentTime_start,
                                  @RequestParam String appointmentTime_end,
                                  @RequestParam Long teacherId,
                                  RedirectAttributes redirectAttributes) {
        List<Appointment> appointments_old = appointmentService.getAllAppointments();
        for (Appointment appointment : appointments_old) {
            if (appointment.getTeacher().getId().equals(teacherId)) {
                LocalDateTime dateTime_new1 = LocalDateTime.parse(appointmentTime_start);
                LocalDateTime dateTime_new2 = LocalDateTime.parse(appointmentTime_end);
                LocalDateTime dateTime_old1 = appointment.getAppointmentTime_start();
                LocalDateTime dateTime_old2 = appointment.getAppointmentTime_end();

                int comparison1 = dateTime_new1.compareTo(dateTime_old2);
                int comparison2 = dateTime_new2.compareTo(dateTime_old1);
                int comparison_start_end = dateTime_new1.compareTo(dateTime_new2);

                if (!(comparison1 > 0 || comparison2 < 0) || comparison_start_end > 0) {
                    redirectAttributes.addFlashAttribute("message", "Time conflict or invalid time range!");
                    return "redirect:/appointments/" + URLEncoder.encode(username, StandardCharsets.UTF_8);
                }
            }
        }
        LocalTime time1=LocalDateTime.parse(appointmentTime_start).toLocalTime();
        LocalTime time2=LocalDateTime.parse(appointmentTime_end).toLocalTime();
        Teacher teacher = teacherService.getTeacherById(teacherId);

        LocalTime time3=teacher.getAllowAppointmentTimeStart();
        LocalTime time4=teacher.getAllowAppointmentTimeEnd();
        if (time3!=null && time4!=null) {

            if((time1.compareTo(time3)<0)||(time2.compareTo(time4)>0)) {
                redirectAttributes.addFlashAttribute("message", "Time conflict or invalid time range!");
                return "redirect:/appointments/" + URLEncoder.encode(username, StandardCharsets.UTF_8);
            }

        }

        Appointment appointment = new Appointment();
        appointment.setStudentName(studentName);
        appointment.setAppointmentTime_start(LocalDateTime.parse(appointmentTime_start));
        appointment.setAppointmentTime_end(LocalDateTime.parse(appointmentTime_end));
        appointment.setTeacher(teacher);
        appointmentService.saveAppointment(appointment);
        redirectAttributes.addFlashAttribute("message", "Appointment created successfully!");
        return "redirect:/appointments/" + URLEncoder.encode(username, StandardCharsets.UTF_8);
    }

    @PostMapping("/delete")
    public String deleteAppointment(@PathVariable("username") String username,@RequestParam Long id) {
        Appointment appointment= appointmentService.getAppointmentById(id);
        if(appointment.getStudentName().equals(username)) {
            appointmentService.deleteAppointment(id);
        }
        return "redirect:/appointments/" + URLEncoder.encode(username, StandardCharsets.UTF_8);
    }
}
