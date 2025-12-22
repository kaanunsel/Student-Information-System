package org.pangea.sis.infrastructure.adapter.in.web.mapper;

import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.infrastructure.adapter.in.web.dto.EnrollmentDTO;

/**
 * Mapper for converting between Enrollment domain model and EnrollmentDTO.
 * Handles transformations between web layer and domain layer.
 */
public class EnrollmentWebMapper {

    /**
     * Converts an EnrollmentDTO to an Enrollment domain model.
     *
     * @param dto the EnrollmentDTO containing enrollment data
     * @return Enrollment domain model
     */
    public static Enrollment toDomain(EnrollmentDTO dto) {
        Enrollment domain = new Enrollment();
        domain.setId(dto.getId());
        domain.setStudentId(dto.getStudentId());
        domain.setCourseId(dto.getCourseId());
        domain.setGrade(dto.getGrade());
        domain.setEnrolledAt(dto.getEnrolledAt());
        return domain;
    }

    /**
     * Converts an Enrollment domain model to an EnrollmentDTO.
     *
     * @param enrollment the Enrollment domain model to convert
     * @return EnrollmentDTO containing enrollment data
     */
    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudentId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        // Note: student/course/instructor names can be populated separately if needed
        return dto;
    }
}
