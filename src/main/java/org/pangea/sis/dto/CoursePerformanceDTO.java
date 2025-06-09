package org.pangea.sis.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object used to represent performance statistics for a course.
 * Includes average, min, max grades and student count for reporting purposes.
 */
public class CoursePerformanceDTO {


    /** ID of the course. */
    @NotNull(message = "Course ID is required")
    private Long courseId;

    /** Name of the course. */
    private String courseName;

    /** Average grade of enrolled students. */
    private Double avgGrade;

    /** Number of students enrolled in the course. */
    private Long numberOfStudents;

    /** Minimum grade received by a student in this course. */
    private Integer minGrade;

    /** Maximum grade received by a student in this course. */
    private Integer maxGrade;

    // --- Constructor ---

    public CoursePerformanceDTO(Long courseId, String courseName, Double avgGrade, Long numberOfStudents, Integer minGrade, Integer maxGrade) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.avgGrade = avgGrade;
        this.numberOfStudents = numberOfStudents;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
    }

    // --- Getters and Setters ---

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(Integer minGrade) {
        this.minGrade = minGrade;
    }

    public Integer getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Integer maxGrade) {
        this.maxGrade = maxGrade;
    }
}