package org.pangea.sis.repository;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enrollment> findByGradeIsNotNull();

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