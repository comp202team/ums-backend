package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> getEnrollmentsByStudent_Id(Long id);
    Enrollment getByEnrollmentId(Long id);
    List<Enrollment> getEnrollmentsByCourse_Id(Long id);

}
