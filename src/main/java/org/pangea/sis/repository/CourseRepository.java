package org.pangea.sis.repository;

import org.pangea.sis.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByCredit(Integer credit);
    List<Course> findAllByNameContainingIgnoreCase(String string);
    List<Course> findAllByCodeContainingIgnoreCase(String string);
    Optional<Course> findByCode(String code);
}
