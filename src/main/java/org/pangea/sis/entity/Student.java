package org.pangea.sis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birth_date;
    private LocalDateTime created_at;
}
