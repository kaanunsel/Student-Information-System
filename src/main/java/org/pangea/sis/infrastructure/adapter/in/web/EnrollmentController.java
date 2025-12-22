package org.pangea.sis.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.domain.port.in.EnrollmentUseCase;
import org.pangea.sis.infrastructure.adapter.in.web.dto.EnrollmentDTO;
import org.pangea.sis.infrastructure.adapter.in.web.mapper.EnrollmentWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing student-course enrollment operations.
 */
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    private final EnrollmentUseCase enrollmentUseCase;

    public EnrollmentController(EnrollmentUseCase enrollmentUseCase) {
        this.enrollmentUseCase = enrollmentUseCase;
    }

    /**
     * Retrieves all enrollments in the system, with optional filtering by student or course.
     *
     * @param studentId Optional ID of the student to filter by.
     * @param courseId  Optional ID of the course to filter by.
     * @return A list of matching {@link EnrollmentDTO} objects.
     */
    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId
    ) {
        if (studentId != null && courseId != null) {
            return enrollmentUseCase.getAllEnrollmentsByStudentAndCourseId(studentId, courseId).stream()
                    .map(EnrollmentWebMapper::toDTO)
                    .toList();
        } else if (studentId != null) {
            return enrollmentUseCase.getByStudentId(studentId).stream()
                    .map(EnrollmentWebMapper::toDTO)
                    .toList();
        } else if (courseId != null) {
            return enrollmentUseCase.getByCourseId(courseId).stream()
                    .map(EnrollmentWebMapper::toDTO)
                    .toList();
        } else {
            return enrollmentUseCase.getAllEnrollments().stream()
                    .map(EnrollmentWebMapper::toDTO)
                    .toList();
        }
    }

    /**
     * Creates a new enrollment using student and course IDs.
     *
     * @param dto enrollment data
     * @return created enrollment as DTO
     */
    @PostMapping
    public ResponseEntity<?> addEnrollment(@RequestBody @Valid EnrollmentDTO dto) {
        Enrollment enrollment = EnrollmentWebMapper.toDomain(dto);
        Enrollment createdEnrollment = enrollmentUseCase.createEnrollment(enrollment);
        return new ResponseEntity<>(EnrollmentWebMapper.toDTO(createdEnrollment), HttpStatus.CREATED);
    }

    /**
     * Updates the grade of a specific enrollment.
     *
     * @param id    ID of the enrollment
     * @param grade new grade to assign
     * @return updated enrollment as DTO
     */
    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(
            @PathVariable Long id,
            @RequestParam Integer grade
    ) {
        Enrollment updated = enrollmentUseCase.updateGrade(id, grade);
        return ResponseEntity.ok(EnrollmentWebMapper.toDTO(updated));
    }

    /**
     * Deletes an enrollment by its ID.
     *
     * @param id enrollment ID
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable Long id) {
        enrollmentUseCase.deleteEnrollment(id);
        return new ResponseEntity<>("Enrollment deleted", HttpStatus.OK);
    }
}
