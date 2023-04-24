package com.comp202.ums.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "assignments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "assignment")
    private Set<Submission> submissions = new HashSet<>();

    // constructors, getters, and setters

    public void addSubmission(Submission submission) {
        submissions.add(submission);
        submission.setAssignment(this);
    }

    public void removeSubmission(Submission submission) {
        submissions.remove(submission);
        submission.setAssignment(null);
    }
}
