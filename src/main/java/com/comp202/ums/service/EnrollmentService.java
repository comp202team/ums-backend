package com.comp202.ums.service;

import com.comp202.ums.Dto.enrollment.EnrollmentMainDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.EnrollmentMapper;
import com.comp202.ums.Map.UserMapper;
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
    public UserDto getInstructor(Long id){
        Course course =courseRepository.findById(id).orElse(null);
        if (course==null)
            throw new NotFoundException("Enrollment","Enrollment Course not found");
        return UserMapper.INSTANCE.userToUserDto(course.getInstructor());
    }
    public List<EnrollmentMainDto> getEnrollmentsFromStudent(User student){
       return EnrollmentMapper.INSTANCE.toEnrollmentDtoList(enrollmentRepository.getEnrollmentsByStudentId(student.getId()));
    }
    public UserDto getStudent(Long id){
        return UserMapper.INSTANCE.userToUserDto(userRepository.findById(id).orElse(null));
    }
    public EnrollmentMainDto saveEnrolment(Enrollment enrollment){
        if (enrollmentRepository.getEnrollmentByCourse_IdAndStudent_Id(enrollment.getCourse().getId(), enrollment.getStudent().getId()) != null)
            throw new IllegalArgumentException("Enrollment already exist");
        return EnrollmentMapper.INSTANCE.toDto(enrollmentRepository.save(enrollment));
    }
    public EnrollmentMainDto getEnrollment(Long id){
        return EnrollmentMapper.INSTANCE.toDto(enrollmentRepository.getByEnrollmentId(id));
    }
    public Enrollment getEnrollmentEntity(Long id){
        return enrollmentRepository.getByEnrollmentId(id);
    }
    public void deleteEnrolment(Long id){
        enrollmentRepository.deleteById(id);
    }
    public EnrollmentMainDto updateEnrolment(Long id, Enrollment enrollment){
        Optional<Enrollment> enrolment1= enrollmentRepository.findById(id);
        if (enrolment1.isPresent()){
            Enrollment found=enrolment1.get();
            found.setEnrollmentId(enrollment.getEnrollmentId());
            found.setEnrolmentDate(enrollment.getEnrolmentDate());
            found.setCourse(enrollment.getCourse());
            found.setStudent(enrollment.getStudent());
            found.setGrade(enrollment.getGrade());
            enrollmentRepository.save(found);
            return  EnrollmentMapper.INSTANCE.toDto(found);
        }else
            return null;
    }




}
