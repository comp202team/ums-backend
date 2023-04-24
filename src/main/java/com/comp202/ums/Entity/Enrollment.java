package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Table(name = "enrollments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollmentId")
    private Long enrollmentId;
    @OneToOne
    @JoinColumn(name = "studentId")
    private User student;
    @OneToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @Column(name = "enrollmentDate")
    private LocalDate enrolmentDate;
    @Column(name = "grade")
    private Double grade;

    public Enrollment(User student, Course course){
        this.student = student;
        this.course=course;
        enrolmentDate=LocalDate.now();
    }
}
