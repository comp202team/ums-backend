package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.Repository.UserRepository;
import com.comp202.ums.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }
    public User getInstructor(Long id){
        Course course =courseRepository.findById(id).orElse(null);
        if (course==null)
            throw new NotFoundException("Enrollment","Enrollment Course not found");
        return course.getInstructor();
    }
    public List<Enrollment> getEnrollmentsFromStudent(User student){
       return enrollmentRepository.getAllEnrollmentsByStudentId(student.getId());
    }
    public User getStudent(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public Enrollment saveEnrolment(Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }
    public Enrollment getEnrollment(Long id){
        return enrollmentRepository.getByEnrollmentId(id);
    }
    public void deleteEnrolment(Long id){
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
