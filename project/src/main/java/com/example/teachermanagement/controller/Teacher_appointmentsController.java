
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
import java.time.LocalTime;
import java.time.LocalDateTime;


import com.example.teachermanagement.model.Teacher;

import com.example.teachermanagement.service.TeacherService;
@Controller
@RequestMapping("/teacher_appointments/{id}")
public class Teacher_appointmentsController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String getAppointments(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.findById(id);

        List<Appointment> appointments = appointmentService.findByTeacherId(id);
        model.addAttribute("id", teacher.getId());
        model.addAttribute("teacher", teacher);
        model.addAttribute("appointments", appointments);
        return "teacher_manage_appointments";

    }

    @PostMapping("/approve")
    public String approveAppointment(RedirectAttributes redirectAttributes,
                                     @RequestParam("appointmentId") Long appointmentId,
                                     @PathVariable("id") Long id) {
        appointmentService.approveAppointment(appointmentId); // 根据ID批准预约
        redirectAttributes.addFlashAttribute("message", "预约已批准！");
        return "redirect:/teacher_appointments/" + id;
    }





//    @PostMapping("/set_appointment")
//    public String setappointment( @RequestParam ("allow_appointmentTime_start") LocalTime allow_appointmentTime_start,
//                                  @RequestParam ("allow_appointmentTime_end") LocalTime allow_appointmentTime_end,
//                                 RedirectAttributes redirectAttributes,@PathVariable("id") Long id)
//    {
//        System.out.println("Allow appointment time start: " + allow_appointmentTime_start);
//       // teacherService.setAppointment_time(id,allow_appointmentTime_start,allow_appointmentTime_end); // 根据ID批准预约
//
//       // appointmentService.approveAppointment(appointmentId); // 根据ID批准预约
//        redirectAttributes.addFlashAttribute("message", "Appointment approved successfully");
//        return "redirect:/teacher_appointments/" + id;
//    }




    @PostMapping("/set_appointment")
    public String setAppointment(
            @RequestParam("allow_appointmentTime_start") LocalTime allowAppointmentTimeStart,
            @RequestParam("allow_appointmentTime_end") LocalTime allowAppointmentTimeEnd,
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        System.out.println("Allow appointment time start: " + allowAppointmentTimeStart);
        System.out.println("Allow appointment time end: " + allowAppointmentTimeEnd);
        teacherService.setAppointmentTime(id, allowAppointmentTimeStart, allowAppointmentTimeEnd); // 设置允许的预约时间段
        redirectAttributes.addFlashAttribute("message", "预约事件设置成功！");
        return "redirect:/teacher_appointments/" + id;
    }












    @PostMapping("/delete")
    public String deleteAppointment(@PathVariable("id") Long id, @RequestParam("appointmentId") Long appointmentId, RedirectAttributes redirectAttributes) {
        appointmentService.deleteAppointment(appointmentId); // 根据ID删除预约
        redirectAttributes.addFlashAttribute("message", "Appointment deleted successfully");
        return "redirect:/teacher_appointments/" + id;
    }


}


