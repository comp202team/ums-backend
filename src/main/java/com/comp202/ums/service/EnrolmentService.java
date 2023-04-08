package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrolment;
import com.comp202.ums.Entity.Instructor;
import com.comp202.ums.Entity.Student;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.EnrolmentRepository;
import com.comp202.ums.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrolmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrolmentRepository enrolmentRepository;

    public EnrolmentService(StudentRepository studentRepository, CourseRepository courseRepository,EnrolmentRepository enrolmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrolmentRepository=enrolmentRepository;
    }
    public Instructor getInstructor(Long id){
        Course course =courseRepository.findById(id).orElse(null);
        return courseRepository.getByInstructor(id);
    }
    public Student getStudent(Long id){
        return studentRepository.findById(id).orElse(null);
    }
    public Optional<Enrolment> getGrade(Student student){
        Enrolment enrolment = enrolmentRepository.getByStudent(student).orElse(null);
        return enrolmentRepository.getGradeByStudent(student.getStudentId());
    }
    public Enrolment createEnrolment(Enrolment enrolment){
        return enrolmentRepository.save(enrolment);
    }
    public void deleteEnrolment(Long id){
        Enrolment enrolment = enrolmentRepository.findById(id).orElse(null);
        enrolmentRepository.deleteById(id);
    }
    public Enrolment updateEnrolment(Long id, Enrolment enrolment){
        Optional<Enrolment> enrolment1=enrolmentRepository.findById(id);
        if (enrolment1.isPresent()){
            Enrolment found=enrolment1.get();
            found.setEnrollmentId(enrolment.getEnrollmentId());
            found.setEnrolmentDate(enrolment.getEnrolmentDate());
            found.setCourse(enrolment.getCourse());
            found.setStudent(enrolment.getStudent());
            found.setGrade(enrolment.getGrade());
            enrolmentRepository.save(found);
            return  found;
        }else
            return null;
    }




}
