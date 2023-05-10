package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> getEnrollmentsByStudentId(Long id);
    Enrollment getByEnrollmentId(Long id);
    List<Enrollment> getEnrollmentsByCourseId(Long id);

}
