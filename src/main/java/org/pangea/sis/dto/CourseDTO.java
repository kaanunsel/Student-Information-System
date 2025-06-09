package org.pangea.sis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Course code is required")
    private String code;
    @NotNull(message = "Credit value is required")
    private Integer credit;

    @NotNull(message = "Instructor Id is required")
    private Long instructorId;

    @NotBlank(message = "Instructor name is required")
    private String instructorName;

    public CourseDTO(){}

    public CourseDTO(String name, String code, Integer credit, Long instructorId, String instructorName){
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Course code is required") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Course code is required") String code) {
        this.code = code;
    }

    public @NotNull(message = "Credit value is required") Integer getCredit() {
        return credit;
    }

    public void setCredit(@NotNull(message = "Credit value is required") Integer credit) {
        this.credit = credit;
    }

    public @NotNull(message = "Instructor name is required") String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(@NotNull(message = "Instructor name is required") String instructorName) {
        this.instructorName = instructorName;
    }

    public @NotNull(message = "Instructor Id is required") Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(@NotNull(message = "Instructor Id is required") Long instructorId) {
        this.instructorId = instructorId;
    }
}
