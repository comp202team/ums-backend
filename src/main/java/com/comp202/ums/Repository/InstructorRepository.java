package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {


}
