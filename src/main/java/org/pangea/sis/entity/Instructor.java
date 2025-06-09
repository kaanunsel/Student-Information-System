package org.pangea.sis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an instructor in the system.
 * Instructors can teach courses and advise students.
 */
@Entity
@Table(name = "instructor")
public class Instructor {

    /** Unique identifier of the instructor. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** First name of the instructor. */
    private String name;

    /** Last name of the instructor. */
    private String surname;

    /** Email address of the instructor (must be valid and not blank). */
    @NotBlank
    @Email
    private String email;

    /** Login password of the instructor (must not be blank). */
    @NotBlank
    private String password;

    /** Date and time when the instructor account was created. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * List of courses taught by this instructor.
     * One instructor can teach multiple courses.
     */
    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

    /**
     * List of students advised by this instructor.
     * One instructor can advise multiple students.
     */
    @OneToMany(mappedBy = "advisor")
    private List<Student> advisedStudents;

    // --- Constructors ---

    public Instructor() {}

    // --- Getters and Setters ---

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

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getAdvisedStudents() {
        return advisedStudents;
    }

    public void setAdvisedStudents(List<Student> advisedStudents) {
        this.advisedStudents = advisedStudents;
    }
}