package com.comp202.ums.Repository;

import com.comp202.ums.Entity.CourseInvitation;
import com.comp202.ums.Entity.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseInvitationRepository extends JpaRepository<CourseInvitation, Long> {
    List<CourseInvitation> findByEmailAndStatus(String email, InvitationStatus status);
    Optional<CourseInvitation> findByCourseIdAndEmail(long course_courseId, String email);
}

