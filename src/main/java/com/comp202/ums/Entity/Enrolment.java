package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Table(name = "Enrolment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollmentId")
    private long enrollmentId;
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @OneToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @Column(name = "enrollmentDate")
    private LocalDate enrolmentDate;
    @Column(name = "grade")
    private double grade;
}
