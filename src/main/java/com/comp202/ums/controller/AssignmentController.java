package com.comp202.ums.controller;

import com.comp202.ums.Dto.assignment.AssignmentCreateDto;
import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Dto.enrollment.EnrollmentMainDto;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Map.EnrollmentMapper;
import com.comp202.ums.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService=assignmentService;
    }
    @GetMapping("/course")
    public ResponseEntity<List<AssignmentDto>> getAllFromCourse(@RequestParam String code){
        return ResponseEntity.ok(assignmentService.getAllFromCourse(code));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getAllFromCourse(@PathVariable Long id){
        return ResponseEntity.ok(assignmentService.getByAssignmentId(id));
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestParam String code, @RequestBody AssignmentCreateDto assignmentCreateDto){
        return ResponseEntity.ok(assignmentService.createAssignment(code,assignmentCreateDto));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AssignmentDto> updateAssignmentNameAndDeadline(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.ok(assignmentService.updateAssignmentNameAndDeadline(id,assignmentDto));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<AssignmentDto> deleteAssignmentById(@PathVariable Long id) {
        Assignment existingAssignment = assignmentService.getAssignmentEntity(id);
        if (existingAssignment!=null) {
            assignmentService.deleteAssignment(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
