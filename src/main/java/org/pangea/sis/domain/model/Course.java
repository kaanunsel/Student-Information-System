package org.pangea.sis.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model representing a course in the system.
 * Pure POJO without infrastructure dependencies.
 */
public class Course {
    private Long id;
    private String name;
    private String code;
    private Integer credit;
    private LocalDateTime createdAt;
    private Long instructorId;

    public Course() {
    }

    public Course(Long id, String name, String code, Integer credit, 
                  LocalDateTime createdAt, Long instructorId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.createdAt = createdAt;
        this.instructorId = instructorId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
