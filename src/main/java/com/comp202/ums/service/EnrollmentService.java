package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.Instructor;
import com.comp202.ums.Entity.Student;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }
    public Instructor getInstructor(Long id){
        Course course =courseRepository.findById(id).orElse(null);
        return courseRepository.getByInstructor(id);
    }
    public Student getStudent(Long id){
        return studentRepository.findById(id).orElse(null);
    }
    public Optional<Enrollment> getGrade(Student student){
        Enrollment enrollment = enrollmentRepository.getByStudent(student).orElse(null);
        return enrollmentRepository.getGradeByStudent(student.getStudentId());
    }
    public Enrollment createEnrolment(Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }
    public void deleteEnrolment(Long id){
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        enrollmentRepository.deleteById(id);
    }
    public Enrollment updateEnrolment(Long id, Enrollment enrollment){
        Optional<Enrollment> enrolment1= enrollmentRepository.findById(id);
        if (enrolment1.isPresent()){
            Enrollment found=enrolment1.get();
            found.setEnrollmentId(enrollment.getEnrollmentId());
            found.setEnrolmentDate(enrollment.getEnrolmentDate());
            found.setCourse(enrollment.getCourse());
            found.setStudent(enrollment.getStudent());
            found.setGrade(enrollment.getGrade());
            enrollmentRepository.save(found);
            return  found;
        }else
            return null;
    }




}
