package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.model.Login;
import com.example.teachermanagement.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherScraperService teacherScraperService;

    @Autowired
    private LoginService loginService;

    public Teacher saveOrUpdateTeacher(Teacher teacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (existingTeacher.isPresent()) {
            Teacher updatedTeacher = existingTeacher.get();
            updatedTeacher.setName(teacher.getName());
            updatedTeacher.setCollege(teacher.getCollege());
            updatedTeacher.setTitle(teacher.getTitle());
            updatedTeacher.setPhone(teacher.getPhone());
            updatedTeacher.setAddress(teacher.getAddress());
            updatedTeacher.setHomepageUrl(teacher.getHomepageUrl());
            updatedTeacher.setResearchDirection(teacher.getResearchDirection());
            return teacherRepository.save(updatedTeacher);
        } else {
            return teacherRepository.save(teacher);
        }
    }

    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    public Teacher findByName(String name) {
        return teacherRepository.findByName(name);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher getAndSaveTeacherInfo(String chineseName, String email, String password) throws IOException {
        Teacher scrapedTeacher = teacherScraperService.scrapeTeacherInfo(chineseName);
        Optional<Teacher> existingTeacher = teacherRepository.findByEmail(scrapedTeacher.getEmail());
        if (existingTeacher.isPresent()) {
            Teacher teacher = existingTeacher.get();
            boolean isUpdated = false;
            if (!teacher.equals(scrapedTeacher)) {
                teacher.setName(scrapedTeacher.getName());
                teacher.setCollege(scrapedTeacher.getCollege());
                teacher.setTitle(scrapedTeacher.getTitle());
                teacher.setPhone(scrapedTeacher.getPhone());
                teacher.setAddress(scrapedTeacher.getAddress());
                teacher.setHomepageUrl(scrapedTeacher.getHomepageUrl());
                teacher.setResearchDirection(scrapedTeacher.getResearchDirection());
                isUpdated = true;
            }
            if (isUpdated) {
                teacherRepository.save(teacher);
            }

            if (email != null && password != null) {
                saveLogin(email, password);
            }
            return teacher;
        } else {
            teacherRepository.save(scrapedTeacher);
            if (email != null && password != null) {
                saveLogin(email, password);
            }
            return scrapedTeacher;
        }
    }

    private void saveLogin(String email, String password) {
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);
        loginService.saveLogin(login);
    }

    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
