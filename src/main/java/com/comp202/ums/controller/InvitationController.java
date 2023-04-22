package com.comp202.ums.controller;

import com.comp202.ums.Dto.invitation.InvitationRequestDto;
import com.comp202.ums.Dto.invitation.InvitationResponseDto;
import com.comp202.ums.service.CourseInvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/invitations")
public class InvitationController {
    private CourseInvitationService courseInvitationService;

    public InvitationController(CourseInvitationService courseInvitationService){
        this.courseInvitationService=courseInvitationService;
    }
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<?> createInvitation(@RequestBody InvitationRequestDto request) {
        for (String email:request.getStudentEmail()) {
            courseInvitationService.createInvitation(email, request.getCourseId());
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{invitationId}")
    public ResponseEntity<?> handleInvitationResponse(@PathVariable Long invitationId, @RequestBody InvitationResponseDto response) {
        courseInvitationService.handleInvitationResponse(invitationId,response.getResponse());
        return ResponseEntity.ok().build();
    }
}
