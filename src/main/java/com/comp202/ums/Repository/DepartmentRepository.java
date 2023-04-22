package com.comp202.ums.Repository;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department getByDepartmentId(Long id);
    Department getDepartmentByDepartmentCode(String deptCode);
}
