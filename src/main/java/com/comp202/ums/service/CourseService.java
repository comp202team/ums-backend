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
import com.comp202.ums.exception.ForbiddenException;
import com.comp202.ums.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final UserService userService;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(UserService userService, CourseRepository courseRepository, UserRepository userRepository, DepartmentRepository departmentRepository
    , EnrollmentRepository enrollmentRepository) {
        this.userService = userService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.departmentRepository=departmentRepository;
        this.enrollmentRepository=enrollmentRepository;

    }

    public List<CourseDto> getAllCourses(){
        return CourseMapper.INSTANCE.toCourseDtoList(courseRepository.findAll());
    }

    public List<CourseDto> getAllCoursesByUserId(Long userId){
        User user = userService.getCurrentUser();
        if(userService.checkInstructor(user)){
            return CourseMapper.INSTANCE.toCourseDtoList(courseRepository.getCoursesByInstructorId(userId));
        }
        else {
            List<Enrollment> enrollments = enrollmentRepository.getAllEnrollmentsByStudentId(userId);
            List<Course> courses = new ArrayList<>();
            for (Enrollment enrollment : enrollments) {
                courses.add(enrollment.getCourse());
            }
            return CourseMapper.INSTANCE.toCourseDtoList(courses);
        }
    }
    public Course getCourseEntity(Long id){
       return getCourseByIdOrElseThrowError(id);
    }

    public CourseDto createCourse(CourseCreateDto courseCreateDto){
        Course course = courseFromCourseCreate(courseCreateDto);
        User user = userService.getCurrentUser();
        if(userService.checkInstructor(user)){
            course.setInstructor(user);
            return CourseMapper.INSTANCE.toDto(courseRepository.save(course));
        }
        throw new ForbiddenException();
    }

    protected Course courseFromCourseCreate(CourseCreateDto courseCreateDto){
        Course course = new Course();
        course.setCourseName(courseCreateDto.getCourseName());
        course.setCourseCode(courseCreateDto.getCourseCode());
        course.setCourseDesc(courseCreateDto.getCourseDesc());
        course.setCreditHours(courseCreateDto.getCreditHours());
        return course;
    }

    protected CourseDto saveCourse(Course course){
        return CourseMapper.INSTANCE.toDto(courseRepository.save(course));
    }
   public CourseDto getCourseById(Long id){
        return CourseMapper.INSTANCE.toDto(getCourseByIdOrElseThrowError(id));
   }
   public CourseDto getTheCourseByCode(String code){
        return CourseMapper.INSTANCE.toDto(courseRepository.getByCourseCode(code));
   }
   public CourseDto changeDepartmentOfCourse(Long id, ChangeCourseDeptDto changeCourseDeptDto){
       Department department = departmentRepository.getDepartmentByDepartmentCode(changeCourseDeptDto.getDepartmentCode());
       Course course = getCourseByIdOrElseThrowError(id);
       course.setDepartment(department);
       return saveCourse(course);
   }
   public CourseDto assignInstructorToCourse(Long id, EmailDto email){
       Course course = courseRepository.getById(id);
       User instructor = userRepository.findByEmail(email.getEmail());
       course.setInstructor(instructor);
       return saveCourse(course);
   }
   public List<CourseDto> getCourseByInstructorEmail(String email){
       User instructor = userRepository.findByEmail(email);
        return CourseMapper.INSTANCE.toCourseDtoList(courseRepository.getCoursesByInstructor(instructor));
   }
   public void deleteCourse(Long id){
        courseRepository.deleteById(id);
   }
   public CourseDto updateCourseById(Long id,CourseDto newCourse){
        User instructor = userRepository.findByEmail(newCourse.getInstructor().getEmail());
       Optional<Course> course = courseRepository.findById(id);
       if(course.isPresent()){
           Course c1=course.get();
           c1.setCourseCode(newCourse.getCourseCode());
           c1.setCourseName(newCourse.getCourseName());
           c1.setDepartment(departmentRepository.getDepartmentByDepartmentCode(newCourse.getDepartmentCode()));
           c1.setCourseDesc(newCourse.getCourseDesc());
           c1.setInstructor(instructor);
           c1.setCreditHours(newCourse.getCreditHours());
           courseRepository.save(c1);
           return CourseMapper.INSTANCE.toDto(c1);
       }else
           return null;
   }
    public List<UserDto> getEnrolledStudentsFromCourseId(Long id){
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByCourse_Id(id);
        List<UserDto> userDtos = new java.util.ArrayList<>(Collections.emptyList());
        for (Enrollment enrollment:enrollments) {
            userDtos.add(UserMapper.INSTANCE.userToUserDto(enrollment.getStudent()));
        }
        return userDtos;
    }

    protected Course getCourseByIdOrElseThrowError(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course", "no Course found with this id"));
    }


}
