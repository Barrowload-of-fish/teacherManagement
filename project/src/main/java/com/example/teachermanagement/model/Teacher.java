package com.example.teachermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

import java.time.LocalDateTime;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String college;
    private String title;
    private String phone;
    private String email;

    private String address;
    private String homepageUrl;
    private String researchDirection;
    private LocalTime allow_appointmentTime_start;
    private LocalTime allow_appointmentTime_end;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Appointment> appointments;



    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getResearchDirection() {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }

    public LocalTime getAllowAppointmentTimeStart() {
        return allow_appointmentTime_start ;
    }

    public LocalTime getAllowAppointmentTimeEnd() {
        return allow_appointmentTime_end;
    }

    public void setAllowAppointmentTimeStart(LocalTime allow_appointmentTime_start ){
        this.allow_appointmentTime_start= allow_appointmentTime_start;
    }

    public void setAllowAppointmentTimeEnd(LocalTime allow_appointmentTime_end) {
        this.allow_appointmentTime_end = allow_appointmentTime_end;
    }
}
