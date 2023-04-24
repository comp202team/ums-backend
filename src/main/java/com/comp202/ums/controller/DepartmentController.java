package com.comp202.ums.controller;

import com.comp202.ums.Dto.department.AddCourseToDeptDto;
import com.comp202.ums.Dto.department.AddStudentToDepartmentDto;
import com.comp202.ums.Dto.department.DepartmentCreateDto;
import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.CourseMapper;
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
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        DepartmentDto department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/code")
    public ResponseEntity<DepartmentDto> getDepartmentByDeptCode(@RequestParam String deptCode) {
        DepartmentDto department = departmentService.getDepartmentByDeptCode(deptCode);
        return ResponseEntity.ok(department);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentCreateDto departmentCreateDto) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentCreateDto));
    }
    @PostMapping("/addcourse")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<DepartmentDto> addCourseToDepartment(@RequestBody AddCourseToDeptDto dto) {
        return ResponseEntity.ok(departmentService.addCourseToDepartment(dto));
    }

    @PostMapping("/addstudent")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<DepartmentDto> addStudentToDepartment(@RequestBody AddStudentToDepartmentDto dto) {
        return ResponseEntity.ok(departmentService.addStudentToDepartment(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @RequestBody DepartmentCreateDto departmentCreateDto) {
        Department department = departmentService.getDepartmentEntity(id);
        if (department!=null) {
            return ResponseEntity.ok(departmentService.updateDept(id,departmentCreateDto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<DepartmentDto> deleteDepartmentById(@PathVariable Long id) {
        Department existingDepartment = departmentService.getDepartmentEntity(id);
        if (existingDepartment!=null) {
            departmentService.deleteDeptById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
