package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> getAssignmentsByCourse_CourseCode(String code);
    List<Assignment> getAssignmentsByCourseId(Long id);
    List<Assignment> getAssignmentsByDeadline(LocalDate date);
    Assignment getAssignmentsById(Long id);

}
