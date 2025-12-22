package org.pangea.sis.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JPA entity representing an enrollment in the persistence layer.
 * Contains infrastructure-specific annotations (JPA).
 */
@Entity
@Table(name = "student_course", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class EnrollmentJpaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** Unique identifier of the enrollment record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The student who is enrolled in the course.
     * Many enrollments can be linked to one student.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentJpaEntity student;

    /**
     * The course that the student is enrolled in.
     * Many enrollments can refer to the same course.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;

    /** Grade given to the student for this course. */
    private Integer grade;

    /** Date and time when the student enrolled in the course. */
    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    // --- Constructors ---

    public EnrollmentJpaEntity() {
    }

    // --- Getters and Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(StudentJpaEntity student) {
        this.student = student;
    }

    public void setCourse(CourseJpaEntity course) {
        this.course = course;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Long getId() {
        return id;
    }

    public StudentJpaEntity getStudent() {
        return student;
    }

    public CourseJpaEntity getCourse() {
        return course;
    }

    public Integer getGrade() {
        return grade;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }
}
