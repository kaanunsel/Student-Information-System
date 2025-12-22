package org.pangea.sis.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain model representing a student in the system.
 * Pure POJO without infrastructure dependencies.
 */
public class Student {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Long advisorId;

    public Student() {
    }

    public Student(Long id, String name, String surname, String email, LocalDate birthDate, 
                   LocalDateTime createdAt, Long advisorId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.advisorId = advisorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Long advisorId) {
        this.advisorId = advisorId;
    }
}
