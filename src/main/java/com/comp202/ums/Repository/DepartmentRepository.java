package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    List<Student> getByEnrolledStudents(Long id);
    List<Course> getByOfferedCourses(Long id);

    Department getByDepartmentId(Long id);
}
