package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        System.out.println("12312312312312");
        appointmentRepository.deleteById(id);

    }
    public List<Appointment> findByTeacherId(Long teacherId) {
        return appointmentRepository.findByTeacherId(teacherId);
    }
}