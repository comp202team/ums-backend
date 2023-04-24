package com.comp202.ums.service;

import com.comp202.ums.Dto.course.ChangeCourseDeptDto;
import com.comp202.ums.Dto.course.CourseCreateDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.CourseMapper;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import com.comp202.ums.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.departmentRepository=departmentRepository;

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
   public CourseDto updateCourseById(Long id,CourseDto newCourse){
        User instructor = userRepository.findByEmail(newCourse.getInstructor().getEmail());
       Optional<Course> course = courseRepository.findById(id);
       if(course.isPresent()){
           Course c1=course.get();
           c1.setCourseCode(newCourse.getCourseCode());
           c1.setCourseName(newCourse.getCourseName());
           c1.setDepartment(departmentRepository.getDepartmentByDepartmentCode(newCourse.getDepartmentCode()));
           c1.setCoursedesc(newCourse.getCoursedesc());
           c1.setInstructor(instructor);
           c1.setCreditHours(newCourse.getCreditHours());
           courseRepository.save(c1);
           return CourseMapper.INSTANCE.toDto(c1);
       }else
           return null;
   }



}
