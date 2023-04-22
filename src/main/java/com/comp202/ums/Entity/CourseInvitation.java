package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.*;
@Table(name = "invitations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CourseInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    // constructors, getters, and setters
}

