package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Enrolment;
import com.comp202.ums.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrolmentRepository extends JpaRepository<Enrolment,Long> {
    @Query("SELECT e.grade FROM Enrolment e where e.student.id=:studentId")
    Optional<Enrolment> getGradeByStudent(Long studentId);
    Optional<Enrolment> getByStudent(Student student);
}
