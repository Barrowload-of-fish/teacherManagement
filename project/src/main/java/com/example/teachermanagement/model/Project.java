package com.example.teachermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId; // 修改字段名称以使用驼峰命名法
    private String name;
    private String laboratory;
    private String description;
    private String leader;
    private String numberOfStudents; // 修改字段名称以使用驼峰命名法
    private String numberOfTeachers; // 修改字段名称以使用驼峰命名法
    private String startDate; // 修改字段名称以使用驼峰命名法
    private String endDate; // 修改字段名称以使用驼峰命名法
    private String status;

    // Getters and setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() { // 修改getter方法名称以使用驼峰命名法
        return name;
    }

    public void setName(String name) { // 修改setter方法名称以使用驼峰命名法
        this.name = name;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfStudents() { // 修改getter方法名称以使用驼峰命名法
        return numberOfStudents;
    }

    public void setNumberOfStudents(String numberOfStudents) { // 修改setter方法名称以使用驼峰命名法
        this.numberOfStudents = numberOfStudents;
    }

    public String getNumberOfTeachers() { // 修改getter方法名称以使用驼峰命名法
        return numberOfTeachers;
    }

    public void setNumberOfTeachers(String numberOfTeachers) { // 修改setter方法名称以使用驼峰命名法
        this.numberOfTeachers = numberOfTeachers;
    }

    public String getStartDate() { // 修改getter方法名称以使用驼峰命名法
        return startDate;
    }

    public void setStartDate(String startDate) { // 修改setter方法名称以使用驼峰命名法
        this.startDate = startDate;
    }

    public String getEndDate() { // 修改getter方法名称以使用驼峰命名法
        return endDate;
    }

    public void setEndDate(String endDate) { // 修改setter方法名称以使用驼峰命名法
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
