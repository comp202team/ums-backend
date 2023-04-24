package com.comp202.ums.service;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.department.AddCourseToDeptDto;
import com.comp202.ums.Dto.department.AddStudentToDepartmentDto;
import com.comp202.ums.Dto.department.DepartmentCreateDto;
import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.CourseMapper;
import com.comp202.ums.Map.DepartmentMapper;
import com.comp202.ums.Map.UserMapper;
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
    public List<DepartmentDto> getAllDepartments() {
        return DepartmentMapper.INSTANCE.toDepartmentDtoList(departmentRepository.findAll());
    }
    public DepartmentDto getDepartmentById(Long id){
        return DepartmentMapper.INSTANCE.toDto(departmentRepository.getByDepartmentId(id));
    }
    public Department getDepartmentEntity(Long id){
        return departmentRepository.getByDepartmentId(id);
    }
    public DepartmentDto getDepartmentByDeptCode(String code){
        return DepartmentMapper.INSTANCE.toDto(departmentRepository.getDepartmentByDepartmentCode(code));
    }
    public List<UserDto> getAllStudentsInThisDeptId(Long depId){
        return getDepartmentById(depId).getEnrolledStudents();
    }
    public DepartmentDto createDepartment (DepartmentCreateDto departmentCreateDto){
        Department department = new Department();
        department.setDepartmentName(departmentCreateDto.getDepartmentName());
        department.setDepartmentCode(departmentCreateDto.getDepartmentCode());
        department.setDepartmentHead(departmentCreateDto.getDepartmentHead());
        return saveDept(department);
    }
    public List<CourseDto> getAllCoursesInThisDept(Long id){
        return getDepartmentById(id).getOfferedCourses();
    }
    public void deleteDeptById(Long id){
        departmentRepository.deleteById(id);
    }
    public DepartmentDto addCourseToDepartment(AddCourseToDeptDto dto){
        Department department = departmentRepository.getDepartmentByDepartmentCode(dto.getDepartmentCode());
        List<Course> courses = department.getOfferedCourses();
        for (String courseCode: dto.getCourseCodes()) {
            Course course = courseRepository.getByCourseCode(courseCode);
            courses.add(course);
        }
        department.setOfferedCourses(courses);
        return saveDept(department);
    }
    public DepartmentDto addStudentToDepartment(AddStudentToDepartmentDto dto){
        Department department = departmentRepository.getDepartmentByDepartmentCode(dto.getDepartmentCode());
        List<User> users = department.getEnrolledStudents();
        for (String studentEmail: dto.getStudentEmail()) {
            User student = userRepository.findByEmail(studentEmail);
            users.add(student);
        }
        department.setEnrolledStudents(users);
        return saveDept(department);
    }
    public DepartmentDto saveDept(Department department){
        return DepartmentMapper.INSTANCE.toDto(departmentRepository.save(department));
    }
    public DepartmentDto updateDept(Long id, DepartmentCreateDto createDto){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isPresent()){
            Department dep=department.get();
            dep.setDepartmentCode(createDto.getDepartmentCode());
            dep.setDepartmentHead(createDto.getDepartmentHead());
            dep.setDepartmentName(createDto.getDepartmentName());
            departmentRepository.save(dep);
            return DepartmentMapper.INSTANCE.toDto(dep);
        }else
            return null;
    }

}
