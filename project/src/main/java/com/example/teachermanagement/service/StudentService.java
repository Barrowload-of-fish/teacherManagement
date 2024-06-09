
package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Student;
import com.example.teachermanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    //保存用户的suid，name和password到数据库中

    public void saveStudent(String suid, String name, String password) {
        Student student = new Student();
        student.setSuid(suid);
        student.setName(name);
        student.setPassword(password);
        studentRepository.save(student);
    }
    //根据suid查询学生信息
    public Student getStudentBySuid(String suid) {
        return studentRepository.findBySuid(suid);
    }
    //根据suid与password查询学生信息
    public Student getStudentBySuidAndPassword(String suid, String password) {
        return studentRepository.findBySuidAndPassword(suid, password);
    }
}