package org.pangea.sis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for transferring course data between layers.
 * Includes validation annotations for request body validation.
 */
public class CourseDTO {

    /** Name of the course (e.g., Logic Programming). Cannot be blank. */
    @NotBlank(message = "Name is required")
    private String name;

    /** Unique course code (e.g., CMPE260). Cannot be blank. */
    @NotBlank(message = "Course code is required")
    private String code;

    /** Credit value of the course (e.g., 4). Cannot be null. */
    @NotNull(message = "Credit value is required")
    private Integer credit;

    /** ID of the instructor assigned to this course. Cannot be null. */
    @NotNull(message = "Instructor Id is required")
    private Long instructorId;

    /** Name of the instructor. Used for display or matching. Cannot be blank. */
    private String instructorName;

    // --- Constructors ---

    public CourseDTO(){}

    public CourseDTO(String name, String code, Integer credit, Long instructorId, String instructorName){
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }

    // --- Getters and Setters ---

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

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName (String instructorName) {
        this.instructorName = instructorName;
    }

    public @NotNull(message = "Instructor Id is required") Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(@NotNull(message = "Instructor Id is required") Long instructorId) {
        this.instructorId = instructorId;
    }
}