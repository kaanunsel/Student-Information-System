package org.pangea.sis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course offered in the system.
 * A course can have many enrollments and one instructor.
 */
@Entity
@Table(name = "course")
public class Course {

    /** Unique identifier of the course. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Name of the course (e.g., Calculus II). */
    private String name;

    /** Unique course code (e.g., MATH202). */
    private String code;

    /** Number of credits the course provides. */
    private Integer credit;

    /** Date and time when the course was created. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * List of students enrolled in the course.
     * Bi-directional mapping to Enrollment entity.
     */
    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    /**
     * Instructor assigned to the course.
     * Many courses can be taught by one instructor.
     */
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}