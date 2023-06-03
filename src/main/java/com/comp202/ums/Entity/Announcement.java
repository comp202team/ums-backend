package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "announcement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="announcement_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "Date")
    private LocalDate date;
    @Column(name = "description")
    private String description;
    @ManyToOne()
    @JoinColumn(name="course_id", referencedColumnName="course_id")
    private Course course;
}
