package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;
    @Column(name = "department_name")
    private String departmentName;
    @Column(name = "department_code")
    private String departmentCode;
    @Column(name = "department_head")
    private String departmentHead;
    @OneToMany(mappedBy = "department")
    private List<Course> offeredCourses;
    @OneToMany(mappedBy = "department")
    private List<User> enrolledStudents;

}
