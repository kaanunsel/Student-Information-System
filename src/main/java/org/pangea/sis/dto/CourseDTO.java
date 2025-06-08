package org.pangea.sis.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Course code is required")
    private String code;
    @NotBlank(message = "Credit value is required")
    private Integer credit;

    public CourseDTO(){}

    public CourseDTO(String name, String code, Integer credit){
        this.name = name;
        this.code = code;
        this.credit = credit;
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

    public @NotBlank(message = "Credit value is required") Integer getCredit() {
        return credit;
    }

    public void setCredit(@NotBlank(message = "Credit value is required") Integer credit) {
        this.credit = credit;
    }
}
