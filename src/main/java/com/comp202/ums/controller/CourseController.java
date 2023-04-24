package com.comp202.ums.controller;

import com.comp202.ums.Dto.course.ChangeCourseDeptDto;
import com.comp202.ums.Dto.course.CourseCreateDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.User;
import com.comp202.ums.service.CourseService;
import com.comp202.ums.service.DepartmentService;
import com.comp202.ums.service.EnrollmentService;
import com.comp202.ums.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private DepartmentService departmentService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, UserService userService, DepartmentService departmentService,EnrollmentService enrollmentService){
        this.enrollmentService=enrollmentService;
        this.courseService=courseService;
        this.userService=userService;
        this.departmentService=departmentService;
    }
    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourse();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }
    @GetMapping("/code")
    public ResponseEntity<CourseDto> getCourseByCourseCode(@RequestParam String courseCode) {
        CourseDto course = courseService.getTheCourseByCode(courseCode);
        return ResponseEntity.ok(course);
    }
    @GetMapping("/instructor")
    public ResponseEntity<List<CourseDto>> getCourseByInstructorMail(@RequestBody EmailDto email) {
        List<CourseDto> courses = courseService.getCourseByInstructorEmail(email.getEmail());
        return ResponseEntity.ok(courses);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseCreateDto courseCreate)
    {
        Course course = courseService.courseFromCourseCreate(courseCreate);
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseCreateDto courseCreateDto) {
        CourseDto course = courseService.getCourse(id);
        if (course!=null) {
            return ResponseEntity.ok(courseService.updateCourseById(id,courseCreateDto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/instructor")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> assignInstructorToCourse(@PathVariable Long id, @RequestBody EmailDto email) {
        Course course = courseService.getCourseEntity(id);
        User instructor = userService.getByEmail(email.getEmail());
        if (course!=null && instructor!= null) {
            course.setInstructor(instructor);
            return ResponseEntity.ok(courseService.assignInstructorToCourse(id,email));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/department")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> changeDepartmentOfCourse(@PathVariable Long id, @RequestBody ChangeCourseDeptDto dto) {
        Course course = courseService.getCourseEntity(id);
        if (course!=null ) {
            return ResponseEntity.ok(courseService.changeDepartmentOfCourse(id,dto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> deleteCourseById(@PathVariable Long id) {
        CourseDto existingCourse = courseService.getCourse(id);
        if (existingCourse!=null) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("{id}/enrolledstudents")
    public ResponseEntity<List<UserDto>> getEnrolledStudents(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getEnrolledStudentsFromCourseId(id));
    }


}
