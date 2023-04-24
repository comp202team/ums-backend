package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;
    @Column(name = "course_code")
    private String courseCode;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String courseDesc;
    @Column(name = "credit_hours")
    private Long creditHours;
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne()
    @JoinColumn(name = "instructor_id")
    private User instructor;
    

}
