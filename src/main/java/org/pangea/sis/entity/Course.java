package org.pangea.sis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String code;
    private Integer credit;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "course",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<StudentCourse> enrollments = new ArrayList<>();
}
