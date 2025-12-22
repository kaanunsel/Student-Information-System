package org.pangea.sis.infrastructure.adapter.in.web.mapper;

import org.pangea.sis.domain.model.Course;
import org.pangea.sis.infrastructure.adapter.in.web.dto.CourseDTO;

/**
 * Mapper for converting between Course domain model and CourseDTO.
 * Handles transformations between web layer and domain layer.
 */
public class CourseWebMapper {

    /**
     * Converts a CourseDTO to a Course domain model.
     *
     * @param dto the CourseDTO containing course data
     * @return Course domain model
     */
    public static Course toDomain(CourseDTO dto) {
        Course domain = new Course();
        domain.setId(dto.getId());
        domain.setName(dto.getName());
        domain.setCode(dto.getCode());
        domain.setCredit(dto.getCredit());
        domain.setInstructorId(dto.getInstructorId());
        return domain;
    }

    /**
     * Converts a Course domain model to a CourseDTO.
     *
     * @param course the Course domain model to convert
     * @return CourseDTO containing simplified course data
     */
    public static CourseDTO toDto(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getCredit(),
                course.getInstructorId(),
                null // instructorName can be populated separately if needed
        );
    }
}
