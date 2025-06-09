package org.pangea.sis.repository;

import org.pangea.sis.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing Course entities.
 * Provides custom queries based on credit, name, and code.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds all courses with the specified credit value.
     *
     * @param credit number of credits
     * @return list of courses with the given credit
     */
    List<Course> findAllByCredit(Integer credit);

    /**
     * Finds all courses whose name contains the given string (case-insensitive).
     *
     * @param string keyword to search in course names
     * @return list of matching courses
     */
    List<Course> findAllByNameContainingIgnoreCase(String string);

    /**
     * Finds all courses whose code contains the given string (case-insensitive).
     *
     * @param string keyword to search in course codes
     * @return list of matching courses
     */
    List<Course> findAllByCodeContainingIgnoreCase(String string);

    /**
     * Finds a course with the exact given code.
     *
     * @param code exact course code
     * @return optional containing the course if found
     */
    Optional<Course> findByCode(String code);

    /**
     * Finds all courses based on instructor id.
     *
     * @param id instructor's id
     * @return list of matching courses
     */
    List<Course> findAllByInstructorId(Long id);
}