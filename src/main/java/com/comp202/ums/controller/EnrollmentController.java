package com.comp202.ums.controller;


import com.comp202.ums.Dto.email.EmailDto;
import com.comp202.ums.Dto.enrollment.EnrollmentDto;
import com.comp202.ums.Entity.Enrollment;
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
        public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@RequestBody EmailDto email) {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsFromStudent(userService.getByEmail(email.getEmail()));
            return ResponseEntity.ok(enrollments);
        }
        @PostMapping
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<Enrollment> createEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
            Enrollment enrollment = new Enrollment(userService.getByEmail(enrollmentDto.getEmail())
                    ,courseService.getTheCourseByCode(enrollmentDto.getCourseCode()) );
            return ResponseEntity.ok(enrollmentService.saveEnrolment(enrollment));
        }
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
            return ResponseEntity.ok(enrollmentService.updateEnrolment(id,enrollment));
        }
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
        public ResponseEntity<Enrollment> deleteEnrollmentById(@PathVariable Long id) {
            Enrollment existingEnrollment = enrollmentService.getEnrollment(id);
            if (existingEnrollment!=null) {
                enrollmentService.deleteEnrolment(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    }
