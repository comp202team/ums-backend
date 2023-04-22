package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    @Query(value = "SELECT e FROM Enrollment e where e.student.id=:studentId", nativeQuery = true)
    List<Enrollment> getAllEnrollmentsByStudentId(Long studentId);
    Enrollment getByEnrollmentId(Long id);

}
