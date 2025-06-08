package org.pangea.sis.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "Student ID must not be null")
    private Long studentId;

    private String studentName;

    private String studentSurname;

    @NotNull(message = "Course ID must not be null")
    private Long courseId;

    private String courseName;

    @Min(value = 0, message = "Grade must be 0 or greater")
    private Integer grade;

    private LocalDateTime enrolledAt;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(Long id, Long studentId, String studentName, String studentSurname, Long courseId, String courseName, Integer grade, LocalDateTime enrolledAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.courseId = courseId;
        this.courseName = courseName;
        this.grade = grade;
        this.enrolledAt = enrolledAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Student ID must not be null") Long getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotNull(message = "Student ID must not be null") Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public @NotNull(message = "Course ID must not be null") Long getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull(message = "Course ID must not be null") Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public @Min(value = 0, message = "Grade must be 0 or greater") Integer getGrade() {
        return grade;
    }

    public void setGrade(@Min(value = 0, message = "Grade must be 0 or greater") Integer grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}