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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<CourseDto>> getAllCourses(@RequestParam Optional<Long> userId) {
        if(userId.isPresent()){
            return new ResponseEntity<>(courseService.getAllCoursesByUserId(userId.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    @GetMapping("/code")
    public ResponseEntity<CourseDto> getCourseByCourseCode(@RequestParam String courseCode) {
        return ResponseEntity.ok(courseService.getTheCourseByCode(courseCode));
    }
    @GetMapping("/instructor")
    public ResponseEntity<List<CourseDto>> getCourseByInstructorMail(@RequestBody EmailDto email) {
        return ResponseEntity.ok(courseService.getCourseByInstructorEmail(email.getEmail()));
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseCreateDto courseCreate)
    {
        return new ResponseEntity<>(courseService.createCourse(courseCreate), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseCreateDto courseCreateDto) {
        CourseDto course = courseService.getCourseById(id);
        if (course!=null) {
            course.setCourseCode(courseCreateDto.getCourseCode());
            course.setCourseName(courseCreateDto.getCourseName());
            course.setCourseDesc(courseCreateDto.getCourseDesc());
            course.setCreditHours(courseCreateDto.getCreditHours());
            return ResponseEntity.ok(courseService.updateCourseById(id,course));
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
        CourseDto existingCourse = courseService.getCourseById(id);
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
