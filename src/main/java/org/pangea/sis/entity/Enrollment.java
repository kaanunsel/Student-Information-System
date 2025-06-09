package org.pangea.sis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents the enrollment of a student in a course.
 * Includes the grade and enrollment date.
 */
@Entity
@Table(name = "student_course")
public class Enrollment {

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
    private Student student;

    /**
     * The course that the student is enrolled in.
     * Many enrollments can refer to the same course.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** Grade given to the student for this course. */
    private Integer grade;

    /** Date and time when the student enrolled in the course. */
    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    // --- Constructors ---

    public Enrollment() {
    }

    // --- Getters and Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
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

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Integer getGrade() {
        return grade;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }
}