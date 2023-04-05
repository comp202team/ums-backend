package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course getByCourseId(Long id);
    Course getByCourseCode(String code);
    Optional<Course> findByCourseCode(String code);

    Instructor getByInstructorId(Long id);
}
