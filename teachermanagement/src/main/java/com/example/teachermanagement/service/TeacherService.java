package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Teacher;
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
            updatedTeacher.setResearchDirection(teacher.getResearchDirection()); // 保存研究方向
            return teacherRepository.save(updatedTeacher);
        } else {
            return teacherRepository.save(teacher);
        }
    }

    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    public List<Teacher> findByName(String name) {
        return teacherRepository.findByName(name);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher getAndSaveTeacherInfo(String chineseName) throws IOException {
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
                teacher.setResearchDirection(scrapedTeacher.getResearchDirection()); // 更新研究方向
                isUpdated = true;
            }
            if (isUpdated) {
                return teacherRepository.save(teacher);
            } else {
                return teacher;
            }
        } else {
            return teacherRepository.save(scrapedTeacher);
        }
    }
}
