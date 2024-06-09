package com.example.teachermanagement.repository;


import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {



}