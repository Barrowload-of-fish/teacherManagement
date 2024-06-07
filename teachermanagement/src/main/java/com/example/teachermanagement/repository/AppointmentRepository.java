package com.example.teachermanagement.repository;


import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {



}