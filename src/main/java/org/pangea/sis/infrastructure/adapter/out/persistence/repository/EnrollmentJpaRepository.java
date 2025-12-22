package org.pangea.sis.infrastructure.adapter.out.persistence.repository;

import org.pangea.sis.application.dto.CoursePerformanceDTO;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository interface for managing EnrollmentJpaEntity.
 * Includes custom queries for student, course, and grade-related operations.
 */
@Repository
public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    /**
     * Finds all enrollments for a given student.
     *
     * @param studentId ID of the student
     * @return list of enrollments
     */
    List<EnrollmentJpaEntity> findByStudentId(Long studentId);

    /**
     * Finds all enrollments for a given course.
     *
     * @param courseId ID of the course
     * @return list of enrollments
     */
    List<EnrollmentJpaEntity> findByCourseId(Long courseId);

    /**
     * Finds a specific enrollment by student and course.
     *
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return optional enrollment record
     */
    Optional<EnrollmentJpaEntity> findByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Checks if an enrollment exists for a given student and course.
     *
     * @param studentId ID of the student
     * @param courseId  ID of the course
     * @return true if an enrollment exists, false otherwise
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Finds all enrollments where a grade is assigned.
     *
     * @return list of graded enrollments
     */
    List<EnrollmentJpaEntity> findByGradeIsNotNull();

    /**
     * Returns average grade, enrollment count, and min/max grades for each course.
     *
     * @return list of course performance summaries
     */
    @Query("""
    SELECT new org.pangea.sis.application.dto.CoursePerformanceDTO(
        sc.course.id,
        sc.course.name,
        AVG(sc.grade),
        COUNT(sc),
        MIN(sc.grade),
        MAX(sc.grade)
    )
    FROM EnrollmentJpaEntity sc
    GROUP BY sc.course.id, sc.course.name
    """)
    List<CoursePerformanceDTO> getCoursePerformanceSummary();
}
