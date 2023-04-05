package com.comp202.ums.service;

import com.comp202.ums.Entity.Student;
import com.comp202.ums.Repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getTheStudent(Long id){
        return studentRepository.findById(id).orElse(null);
    }
    public Student createStudent(Student newStudent){
        return studentRepository.save(newStudent);
    }
    public Student updateTheStudent(Long id,Student newStudent){
        Optional<Student> stundent=studentRepository.findById(id);
        if (stundent.isPresent()){
            Student found=stundent.get();
            found.setId(newStudent.getId());
            found.setDob(newStudent.getDob());
            found.setEmail(newStudent.getEmail());
            found.setAddress(newStudent.getAddress());
            found.setDepartment(newStudent.getDepartment());
            found.setPhone(newStudent.getPhone());
            found.setFirstName(newStudent.getFirstName());
            found.setLastName(newStudent.getLastName());
            studentRepository.save(found);
            return found;
        }else
            return null;
    }
    public void deleteStudent(Long id){

        Student student = studentRepository.findById(id).orElse(null);
        studentRepository.deleteById(id);
    }







}
