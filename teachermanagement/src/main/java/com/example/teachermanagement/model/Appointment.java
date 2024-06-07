package com.example.teachermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private LocalDateTime appointmentTime_start;

    @Column(nullable = false)
    private LocalDateTime appointmentTime_end;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getAppointmentTime_start() {
        return appointmentTime_start;
    }
    public LocalDateTime getAppointmentTime_end() {
        return appointmentTime_end;
    }

    public void setAppointmentTime_start(LocalDateTime appointmentTime_start) {
        this.appointmentTime_start = appointmentTime_start;
    }
    public void setAppointmentTime_end(LocalDateTime appointmentTime_end) {
        this.appointmentTime_end = appointmentTime_end;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}