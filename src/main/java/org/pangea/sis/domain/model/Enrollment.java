package org.pangea.sis.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model representing an enrollment of a student in a course.
 * Pure POJO without infrastructure dependencies.
 */
public class Enrollment {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer grade;
    private LocalDateTime enrolledAt;

    public Enrollment() {
    }

    public Enrollment(Long id, Long studentId, Long courseId, Integer grade, LocalDateTime enrolledAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.enrolledAt = enrolledAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
