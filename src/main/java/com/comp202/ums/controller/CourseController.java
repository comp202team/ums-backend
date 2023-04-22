package com.comp202.ums.controller;

import com.comp202.ums.Dto.course.ChangeCourseDeptDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.CourseMapper;
import com.comp202.ums.service.CourseService;
import com.comp202.ums.service.DepartmentService;
import com.comp202.ums.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    private CourseService courseService;
    private UserService userService;
    private DepartmentService departmentService;

    public CourseController(CourseService courseService, UserService userService, DepartmentService departmentService){

        this.courseService=courseService;
        this.userService=userService;
        this.departmentService=departmentService;
    }
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourse();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }
    @GetMapping("/code")
    public ResponseEntity<Course> getCourseByCourseCode(@RequestParam String courseCode) {
        Course course = courseService.getTheCourseByCode(courseCode);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto courseCreate)
    {
        Course course = CourseMapper.INSTANCE.DtoToCourse(courseCreate);
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course course = courseService.getCourse(id);
        if (course!=null) {
            course.setCourseCode(courseDto.getCourseCode());
            course.setCourseName(courseDto.getCourseName());
            course.setCoursedesc(courseDto.getCoursedesc());
            course.setCreditHours(courseDto.getCreditHours());
            return ResponseEntity.ok(courseService.updateCourseById(id,course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/instructor")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Course> assignInstructorToCourse(@PathVariable Long id, @RequestBody EmailDto email) {
        Course course = courseService.getCourse(id);
        User instructor = userService.getByEmail(email.getEmail());
        if (course!=null && instructor!= null) {
            course.setInstructor(instructor);
            return ResponseEntity.ok(courseService.updateCourseById(id,course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/department")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Course> changeDepartmentOfCourse(@PathVariable Long id, @RequestBody ChangeCourseDeptDto dto) {
        Course course = courseService.getCourse(id);
        Department department = departmentService.getDepartmentByDeptCode(dto.getDepartmentCode());
        if (course!=null ) {
            course.setDepartment(department);
            return ResponseEntity.ok(courseService.updateCourseById(id,course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Course> deleteCourseById(@PathVariable Long id) {
        Course existingCourse = courseService.getCourse(id);
        if (existingCourse!=null) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
