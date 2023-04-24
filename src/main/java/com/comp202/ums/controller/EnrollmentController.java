package com.comp202.ums.controller;


import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Dto.enrollment.EnrollmentCreateDto;
import com.comp202.ums.Dto.enrollment.EnrollmentMainDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Map.CourseMapper;
import com.comp202.ums.Map.EnrollmentMapper;
import com.comp202.ums.service.CourseService;
import com.comp202.ums.service.EnrollmentService;
import com.comp202.ums.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/enrollments")
public class EnrollmentController {
        private EnrollmentService enrollmentService;
        private UserService userService;
        private CourseService courseService;
        public EnrollmentController(EnrollmentService enrollmentService,UserService userService, CourseService courseService){
            this.enrollmentService = enrollmentService;
            this.userService=userService;
            this.courseService=courseService;
        }
        @GetMapping("/student")
        public ResponseEntity<List<EnrollmentMainDto>> getEnrollmentsByStudent(@RequestBody EmailDto email) {
            List<EnrollmentMainDto> enrollments = enrollmentService.getEnrollmentsFromStudent(userService.getByEmail(email.getEmail()));
            return ResponseEntity.ok(enrollments);
        }
        @PostMapping
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<EnrollmentMainDto> createEnrollment(@RequestBody EnrollmentCreateDto enrollmentCreateDto) {
            Course course = courseService.getCourseEntity(courseService.getTheCourseByCode(enrollmentCreateDto.getCourseCode()).getId()) ;
            Enrollment enrollment = new Enrollment(userService.getByEmail(enrollmentCreateDto.getEmail()),course);
            return ResponseEntity.ok(enrollmentService.saveEnrolment(enrollment));
        }
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<EnrollmentMainDto> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
            return ResponseEntity.ok(enrollmentService.updateEnrolment(id,enrollment));
        }
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<EnrollmentMainDto> deleteEnrollmentById(@PathVariable Long id) {
            Enrollment existingEnrollment = EnrollmentMapper.INSTANCE.toEntity(enrollmentService.getEnrollment(id));
            if (existingEnrollment!=null) {
                enrollmentService.deleteEnrolment(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    }
