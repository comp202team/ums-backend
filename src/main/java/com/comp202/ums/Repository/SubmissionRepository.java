package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> getSubmissionsByAssignment_Id(Long id);
}
