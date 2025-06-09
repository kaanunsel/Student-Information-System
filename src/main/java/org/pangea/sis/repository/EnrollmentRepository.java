package org.pangea.sis.repository;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Enrollment entities.
 * Includes custom queries for student, course, and grade-related operations.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * Finds all enrollments for a given student.
     *
     * @param studentId ID of the student
     * @return list of enrollments
     */
    List<Enrollment> findByStudentId(Long studentId);

    /**
     * Finds all enrollments for a given course.
     *
     * @param courseId ID of the course
     * @return list of enrollments
     */
    List<Enrollment> findByCourseId(Long courseId);

    /**
     * Finds a specific enrollment by student and course.
     *
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return optional enrollment record
     */
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Finds all enrollments where a grade is assigned.
     *
     * @return list of graded enrollments
     */
    List<Enrollment> findByGradeIsNotNull();

    /**
     * Returns average grade, enrollment count, and min/max grades for each course.
     *
     * @return list of course performance summaries
     */
    @Query("""
    SELECT new org.pangea.sis.dto.CoursePerformanceDTO(
        sc.course.id,
        sc.course.name,
        AVG(sc.grade),
        COUNT(sc),
        MIN(sc.grade),
        MAX(sc.grade)
    )
    FROM Enrollment sc
    GROUP BY sc.course.id, sc.course.name
    """)
    List<CoursePerformanceDTO> getCoursePerformanceSummary();
}