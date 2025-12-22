package org.pangea.sis.infrastructure.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a student in the persistence layer.
 * Contains infrastructure-specific annotations (JPA, Jackson).
 */
@Entity
@Table(name = "student")
public class StudentJpaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** Unique identifier of the student. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** First name of the student. */
    private String name;

    /** Last name of the student. */
    private String surname;

    /** Email address of the student. */
    private String email;

    /** Birth date of the student in yyyy-MM-dd format. */
    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /** Date and time when the student was created (registered). */
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    /**
     * List of course enrollments by this student.
     * This is a one-to-many relationship to Enrollment.
     * Marked as @JsonIgnore to prevent circular reference in JSON.
     */
    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<EnrollmentJpaEntity> enrollments = new ArrayList<>();

    /**
     * Instructor who acts as an academic advisor to the student.
     */
    @ManyToOne
    @JoinColumn(name = "advisor_id")
    private InstructorJpaEntity advisor;

    // --- Constructors ---

    public StudentJpaEntity() {
    }

    // --- Getters and Setters ---

    public List<EnrollmentJpaEntity> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentJpaEntity> enrollments) {
        this.enrollments = enrollments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public InstructorJpaEntity getAdvisor() {
        return advisor;
    }

    public void setAdvisor(InstructorJpaEntity advisor) {
        this.advisor = advisor;
    }
}
