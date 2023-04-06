package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.Student;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import com.comp202.ums.Repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public DepartmentService(DepartmentRepository departmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    public Department getDepartment(Long id){
        Department dep = departmentRepository.findById(id).orElse(null);
        return departmentRepository.getByDepartmentId(id);
    }
    public List<Student> getAllStudentsInThisDeptId(Long depId){
        return getDepartment(depId).getEnrolledStudents();
    }
    public List<Course> getAllCoursesInThisDept(Long id){
        return getDepartment(id).getOfferedCourses();
    }
    public void deleteDept(Long id){
        Department department=departmentRepository.findById(id).orElse(null);
        departmentRepository.deleteById(id);
    }
    public Department createDept(Department department){
        return departmentRepository.save(department);
    }
    public Department updateDept(Long id, Department newdepartment){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isPresent()){
            Department dep=department.get();
            dep.setDepartmentId(newdepartment.getDepartmentId());
            dep.setDepartmentCode(newdepartment.getDepartmentCode());
            dep.setDepartmentHead(newdepartment.getDepartmentHead());
            dep.setDepartmentName(newdepartment.getDepartmentName());
            dep.setOfferedCourses(newdepartment.getOfferedCourses());
            dep.setEnrolledStudents(newdepartment.getEnrolledStudents());
            departmentRepository.save(dep);
            return dep;
        }else
            return null;
    }

}
