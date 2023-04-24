package com.comp202.ums.controller;

import com.comp202.ums.Dto.submission.SubmissionCreateDto;
import com.comp202.ums.Dto.submission.SubmissionDto;
import com.comp202.ums.Dto.submission.SubmissionGradingDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Submission;
import com.comp202.ums.service.SubmissionService;
import com.comp202.ums.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final UserService userService;
    public SubmissionController(SubmissionService submissionService,UserService userService){
        this.submissionService=submissionService;
        this.userService=userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<SubmissionDto>> getAllSubmissionsFromAssignmentId(@PathVariable Long id){
        return ResponseEntity.ok(submissionService.getAllSubmissionsFromAssignmentId(id));
    }
    @PostMapping("/{assignmentId}")
    public ResponseEntity<SubmissionDto> createSubmission(@PathVariable Long assignmentId, @RequestBody SubmissionCreateDto submissionCreateDto){
        UserDto student = userService.getCurrentUserDto();
        return ResponseEntity.ok(submissionService.createSubmission(student.getEmail(), assignmentId,submissionCreateDto));
    }
    @PutMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> updateGrading(@PathVariable Long submissionId,@RequestBody SubmissionGradingDto submissionGradingDto){
        return ResponseEntity.ok(submissionService.updateGrading(submissionId,submissionGradingDto));
    }
    @PatchMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> updateSubmission(@PathVariable Long submissionId, @RequestBody SubmissionCreateDto submission){
        return ResponseEntity.ok(submissionService.updateSubmission(submissionId,submission));
    }
    @DeleteMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> deleteSubmission(@PathVariable Long submissionId){
        Submission existingSubmission = submissionService.getSubmissionEntity(submissionId);
        if (existingSubmission!=null) {
            submissionService.deleteSubmission(submissionId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
