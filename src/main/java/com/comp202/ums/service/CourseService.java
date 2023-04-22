package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository ) {
        this.courseRepository = courseRepository;

    }

    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public Course createCourse(Course newCourse){
        return courseRepository.save(newCourse);
    }
   public Course getCourse(Long id){
        Course course =courseRepository.findById(id).orElse(null);
        return courseRepository.getById(id);
   }
   public Course getTheCourseByCode(String code){
        return courseRepository.getByCourseCode(code);
   }
   public void deleteCourse(Long id){
        courseRepository.deleteById(id);
   }
   public Course updateCourseById(Long id,Course newCourse){
       Optional<Course> course = courseRepository.findById(id);
       if(course.isPresent()){
           Course c1=course.get();
           c1.setId(newCourse.getId());
           c1.setCourseCode(newCourse.getCourseCode());
           c1.setCourseName(newCourse.getCourseName());
           c1.setDepartment(newCourse.getDepartment());
           c1.setCoursedesc(newCourse.getCoursedesc());
           c1.setInstructor(newCourse.getInstructor());
           c1.setCreditHours(newCourse.getCreditHours());
           courseRepository.save(c1);
           return c1;
       }else
           return null;
   }



}
