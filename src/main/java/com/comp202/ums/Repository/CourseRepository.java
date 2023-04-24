package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course getById(Long id);
    Course getByCourseCode(String code);
    Optional<Course> findByCourseCode(String code);

    List<Course> getCoursesByInstructor(User instructor);
}
