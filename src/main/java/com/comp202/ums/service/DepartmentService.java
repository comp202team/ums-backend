package com.comp202.ums.service;

import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Repository.CourseRepository;
import com.comp202.ums.Repository.DepartmentRepository;
import com.comp202.ums.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;


    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department getDepartmentById(Long id){
        return departmentRepository.getByDepartmentId(id);
    }
    public Department getDepartmentByDeptCode(String code){
        return departmentRepository.getDepartmentByDepartmentCode(code);
    }
    public List<User> getAllStudentsInThisDeptId(Long depId){
        return getDepartmentById(depId).getEnrolledStudents();
    }
    public List<Course> getAllCoursesInThisDept(Long id){
        return getDepartmentById(id).getOfferedCourses();
    }
    public void deleteDeptById(Long id){
        departmentRepository.deleteById(id);
    }
    public Department saveDept(Department department){
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
