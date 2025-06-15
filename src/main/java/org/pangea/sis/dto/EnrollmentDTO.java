package org.pangea.sis.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for enrollment information.
 * Used to transfer student-course enrollment data between layers.
 */
public class EnrollmentDTO {

    /** Unique identifier of the enrollment record. */
    private Long id;

    /** ID of the student who is enrolled. Cannot be null. */
    @NotNull(message = "Student ID must not be null")
    private Long studentId;

    /** First name of the student (optional for display). */
    private String studentName;

    /** Last name of the student (optional for display). */
    private String studentSurname;

    /** ID of the course the student is enrolled in. Cannot be null. */
    @NotNull(message = "Course ID must not be null")
    private Long courseId;

    /** Name of the course (optional for display). */
    private String courseName;

    /** ID of the instructor for the course. */
    private Long instructorId;

    /** First name of the instructor (optional for display). */
    private String instructorName;

    /** Last name of the instructor (optional for display). */
    private String instructorSurname;

    /** Grade assigned for the course. Must be 0 or greater. */
    @Min(value = 0, message = "Grade must be 0 or greater")
    private Integer grade;

    /** Date and time when the student enrolled in the course. */
    private LocalDateTime enrolledAt;

    // --- Constructors ---

    public EnrollmentDTO() {
    }

    /**
     * Constructs a new EnrollmentDTO with all field values.
     *
     * @param id The unique identifier of the enrollment.
     * @param studentId The ID of the enrolled student.
     * @param studentName The first name of the student.
     * @param studentSurname The last name of the student.
     * @param courseId The ID of the course.
     * @param courseName The name of the course.
     * @param instructorId The ID of the course's instructor.
     * @param instructorName The first name of the instructor.
     * @param instructorSurname The last name of the instructor.
     * @param grade The grade received.
     * @param enrolledAt The timestamp of the enrollment.
     */
    public EnrollmentDTO(Long id, Long studentId, String studentName, String studentSurname, Long courseId, String courseName, Long instructorId, String instructorName, String instructorSurname, Integer grade, LocalDateTime enrolledAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorSurname = instructorSurname;
        this.grade = grade;
        this.enrolledAt = enrolledAt;
    }

    // --- Getters and Setters ---

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

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorSurname() {
        return instructorSurname;
    }

    public void setInstructorSurname(String instructorSurname) {
        this.instructorSurname = instructorSurname;
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