package com.comp202.ums.service;

import com.comp202.ums.Dto.course.ChangeCourseDeptDto;
import com.comp202.ums.Dto.course.CourseCreateDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.CourseMapper;
import com.comp202.ums.Map.UserMapper;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.Repository.UserRepository;
import com.comp202.ums.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, DepartmentRepository departmentRepository
    ,EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.departmentRepository=departmentRepository;
        this.enrollmentRepository=enrollmentRepository;

    }

    public List<CourseDto> getAllCourse(){
        return CourseMapper.INSTANCE.toCourseDtoList(courseRepository.findAll());
    }
    public Course getCourseEntity(Long id){
       return courseRepository.getById(id);
    }

    public CourseDto createCourse(Course course){
        return CourseMapper.INSTANCE.toDto(courseRepository.save(course));
    }
    public Course courseFromCourseCreate(CourseCreateDto courseCreateDto){
        Course course = new Course();
        course.setCourseName(courseCreateDto.getCourseName());
        course.setCourseCode(courseCreateDto.getCourseCode());
        course.setCoursedesc(courseCreateDto.getCoursedesc());
        course.setCreditHours(courseCreateDto.getCreditHours());
        return course;
    }
   public CourseDto getCourse(Long id){
        return CourseMapper.INSTANCE.toDto(courseRepository.getById(id));
   }
   public CourseDto getTheCourseByCode(String code){
        return CourseMapper.INSTANCE.toDto(courseRepository.getByCourseCode(code));
   }
   public CourseDto changeDepartmentOfCourse(Long id, ChangeCourseDeptDto changeCourseDeptDto){
       Department department = departmentRepository.getDepartmentByDepartmentCode(changeCourseDeptDto.getDepartmentCode());
       Course course = courseRepository.getById(id);
       course.setDepartment(department);
       return createCourse(course);
   }
   public CourseDto assignInstructorToCourse(Long id, EmailDto email){
       Course course = courseRepository.getById(id);
       User instructor = userRepository.findByEmail(email.getEmail());
       course.setInstructor(instructor);
       return createCourse(course);
   }
   public List<CourseDto> getCourseByInstructorEmail(String email){
       User instructor = userRepository.findByEmail(email);
        return CourseMapper.INSTANCE.toCourseDtoList(courseRepository.getCoursesByInstructor(instructor));
   }
   public void deleteCourse(Long id){
        courseRepository.deleteById(id);
   }
   public CourseDto updateCourseById(Long id,CourseCreateDto newCourse){
        Course course = courseRepository.findById(id).orElseThrow(()-> new NotFoundException("Course","Course not Found"));
        course.setCourseCode(newCourse.getCourseCode());
        course.setCourseName(newCourse.getCourseName());
        course.setCoursedesc(newCourse.getCoursedesc());
        course.setCreditHours(newCourse.getCreditHours());
        return CourseMapper.INSTANCE.toDto(courseRepository.save(course));
   }
    public List<UserDto> getEnrolledStudentsFromCourseId(Long id){
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByCourse_Id(id);
        List<UserDto> userDtos = new java.util.ArrayList<>(Collections.emptyList());
        for (Enrollment enrollment:enrollments) {
            userDtos.add(UserMapper.INSTANCE.userToUserDto(enrollment.getStudent()));
        }
        return userDtos;
    }


}
