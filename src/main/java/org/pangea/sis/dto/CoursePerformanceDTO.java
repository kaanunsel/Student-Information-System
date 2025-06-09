package org.pangea.sis.dto;

public class CoursePerformanceDTO {
    private Long courseId;

    private String courseName;

    private Double avgGrade;

    private Long numberOfStudents;

    private Integer minGrade;

    private Integer maxGrade;

    public CoursePerformanceDTO(Long courseId, String courseName, Double avgGrade, Long numberOfStudents, Integer minGrade, Integer maxGrade) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.avgGrade = avgGrade;
        this.numberOfStudents = numberOfStudents;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
    }

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
