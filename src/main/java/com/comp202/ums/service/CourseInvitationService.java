package com.comp202.ums.service;

import com.comp202.ums.Entity.*;
import com.comp202.ums.Repository.CourseInvitationRepository;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.EnrollmentRepository;
import com.comp202.ums.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import static com.comp202.ums.Entity.InvitationResponse.ACCEPT;
import static com.comp202.ums.Entity.InvitationResponse.DECLINE;

@Service
public class CourseInvitationService {

    private CourseInvitationRepository invitationRepository;

    private UserRepository userRepository;

    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private EmailService emailService;
    public CourseInvitationService(EmailService emailService,CourseInvitationRepository invitationRepository,UserRepository userRepository, EnrollmentRepository enrollmentRepository, CourseRepository courseRepository){
        this.userRepository=userRepository;
        this.emailService=emailService;
        this.enrollmentRepository=enrollmentRepository;
        this.invitationRepository=invitationRepository;
        this.courseRepository=courseRepository;
    }

    public CourseInvitation createInvitation(String email, Long courseId) {
        User student = userRepository.findByEmail(email);
        Course course = courseRepository.getById(courseId);

        CourseInvitation invitation = new CourseInvitation();
        invitation.setCourse(course);
        invitation.setEmail(student.getEmail());
        invitation.setStatus(InvitationStatus.PENDING);

        invitation = invitationRepository.save(invitation);

        String invitationLink = generateInvitationLink(invitation.getId());
        emailService.sendInvitationEmail(invitation.getEmail(), invitationLink,course.getCourseName());

        return invitation;
    }

    public void handleInvitationResponse(Long invitationId, InvitationResponse response) {
        CourseInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation not found"));
        User user = userRepository.findByEmail(invitation.getEmail());
        Course course = invitation.getCourse();
        Enrollment enrollment = new Enrollment(user,course);

        if (response == ACCEPT) {
            invitation.setStatus(InvitationStatus.ACCEPTED);
            enrollmentRepository.save(enrollment);
        } else if (response == DECLINE) {
            invitation.setStatus(InvitationStatus.DECLINED);
        } else {
            throw new IllegalArgumentException("Invalid invitation response: " + response);
        }

        invitationRepository.save(invitation);
    }

    private String generateInvitationLink(Long invitationId) {
        // Generate a unique link for the invitation using the invitationId
        return "https://example.com/invitations/" + invitationId;
    }
}
