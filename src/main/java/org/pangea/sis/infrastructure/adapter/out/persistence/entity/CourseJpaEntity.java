package org.pangea.sis.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a course in the persistence layer.
 * Contains infrastructure-specific annotations (JPA).
 */
@Entity
@Table(name = "course")
public class CourseJpaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

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
     * Bidirectional mapping to Enrollment entity.
     */
    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<EnrollmentJpaEntity> enrollments = new ArrayList<>();

    /**
     * Instructor assigned to the course.
     * Many courses can be taught by one instructor.
     */
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private InstructorJpaEntity instructor;

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

    public List<EnrollmentJpaEntity> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentJpaEntity> enrollments) {
        this.enrollments = enrollments;
    }

    public InstructorJpaEntity getInstructor() {
        return instructor;
    }

    public void setInstructor(InstructorJpaEntity instructor) {
        this.instructor = instructor;
    }
}
