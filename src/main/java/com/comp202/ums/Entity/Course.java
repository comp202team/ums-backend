package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "Course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseId")
    private long courseId;
    @Column(name = "courseCode")
    private String courseCode;
    @Column(name = "courseName")
    private String courseName;
    @Column(name = "courseDescription")
    private String coursedesc;
    @Column(name = "creditHours")
    private long creditHours;
    @ManyToOne()
    @JoinColumn(name = "departmentId")
    private Department department;

    @ManyToOne()
    @JoinColumn(name = "instructorId")
    private Instructor instructor;

    @ManyToMany
    @JoinTable(
            name = "students",
            joinColumns = @JoinColumn(name = "stutendId"),
            inverseJoinColumns = @JoinColumn(name = "courseId")
    )
    private List<Student> enrolledStudents;
    

}
