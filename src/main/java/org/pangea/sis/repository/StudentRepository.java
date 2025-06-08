package org.pangea.sis.repository;

import org.pangea.sis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByName(String name);
    List<Student> findAllBySurname(String surname);
    Optional<Student> findByEmail(String email);
}
