package com.comp202.ums.controller;

import com.comp202.ums.Dto.department.AddCourseToDeptDto;
import com.comp202.ums.Dto.department.AddStudentToDepartmentDto;
import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.DepartmentMapper;
import com.comp202.ums.service.CourseService;
import com.comp202.ums.service.DepartmentService;
import com.comp202.ums.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {
    private DepartmentService departmentService;
    private CourseService courseService;
    private UserService userService;
    public DepartmentController(DepartmentService departmentService, CourseService courseService, UserService userService){
        this.departmentService=departmentService;
        this.courseService=courseService;
        this.userService=userService;
    }
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/code")
    public ResponseEntity<Department> getDepartmentByDeptCode(@RequestParam String deptCode) {
        Department department = departmentService.getDepartmentByDeptCode(deptCode);
        return ResponseEntity.ok(department);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = DepartmentMapper.INSTANCE.CreateDtoToEntity(departmentDto);
        return ResponseEntity.ok(departmentService.saveDept(department));
    }
    @PostMapping("/addcourse")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Department> addCourseToDepartment(@RequestBody AddCourseToDeptDto dto) {
        Department department = departmentService.getDepartmentByDeptCode(dto.getDepartmentCode());
        for (String courseCode: dto.getCourseCodes()) {
            Course course = courseService.getTheCourseByCode(courseCode);
            department.getOfferedCourses().add(course);
        }
        return ResponseEntity.ok(departmentService.saveDept(department));
    }

    @PostMapping("/addstudent")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Department> addStudentToDepartment(@RequestBody AddStudentToDepartmentDto dto) {
        Department department = departmentService.getDepartmentByDeptCode(dto.getDepartmentCode());
        for (String studentEmail: dto.getStudentEmail()) {
            User student = userService.getByEmail(studentEmail);
            department.getEnrolledStudents().add(student);
        }
        return ResponseEntity.ok(departmentService.saveDept(department));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.getDepartmentById(id);
        if (department!=null) {
            department.setDepartmentName(departmentDto.getDepartmentName());
            department.setDepartmentCode(departmentDto.getDepartmentCode());
            department.setDepartmentHead(departmentDto.getDepartmentHead());
            return ResponseEntity.ok(departmentService.updateDept(id,department));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Department> deleteDepartmentById(@PathVariable Long id) {
        Department existingDepartment = departmentService.getDepartmentById(id);
        if (existingDepartment!=null) {
            departmentService.deleteDeptById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
