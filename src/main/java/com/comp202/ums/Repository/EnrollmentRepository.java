package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    @Query("SELECT e.grade FROM Enrollment e where e.student.studentId=:studentId")
    Optional<Enrollment> getGradeByStudent(Long studentId);
    Optional<Enrollment> getByStudent(Student student);
}
