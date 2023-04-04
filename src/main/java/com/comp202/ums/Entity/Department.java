package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "Department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentId")
    private long departmentId;
    @Column(name = "departmentName")
    private String departmentName;
    @Column(name = "departmentCode")
    private String departmentCode;
    @Column(name = "departmentHead")
    private String departmentHead;
    @OneToMany(mappedBy = "department")
    private List<Course> offeredCourses;
    @OneToMany(mappedBy = "department")
    private List<Student> enrolledStudents;

}
