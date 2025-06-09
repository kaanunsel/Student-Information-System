package org.pangea.sis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class StudentDTO {

    private Long studentId;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Not a valid email")
    private String email;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private Long advisorId;

    private String advisorName;

    public StudentDTO() {}

    public StudentDTO(String name, String surname, String email, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
    }

    public StudentDTO(Long studentId, String name, String surname, String email, LocalDate birthDate, Long advisorId, String advisorName) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.advisorId = advisorId;
        this.advisorName = advisorName;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Surname is required") String getSurname() {
        return surname;
    }

    public void setSurname(@NotBlank(message = "Surname is required") String surname) {
        this.surname = surname;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Not a valid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Not a valid email") String email) {
        this.email = email;
    }

    public @NotNull(message = "Birth date is required") @Past(message = "Birth date must be in the past") LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull(message = "Birth date is required") @Past(message = "Birth date must be in the past") LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public Long getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Long advisorId) {
        this.advisorId = advisorId;
    }
}