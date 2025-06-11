package org.pangea.sis.dto;

import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;

/**
 * Mapper class for converting between Course and CourseDTO.
 * Helps decouple entity logic from data transfer logic.
 */
public class CourseMapper {

    /**
     * Converts a CourseDTO and Instructor to a Course entity.
     *
     * @param dto        the CourseDTO containing course data
     * @param instructor the instructor entity to be assigned to the course
     * @return a Course entity populated with data from the DTO
     */
    public static Course toEntity(CourseDTO dto, Instructor instructor){
        Course entity = new Course();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCredit(dto.getCredit());
        entity.setInstructor(instructor);
        return entity;
    }

    /**
     * Converts a Course entity to a CourseDTO.
     *
     * @param course the Course entity to convert
     * @return a CourseDTO containing simplified course data
     */
    public static CourseDTO toDto(Course course){
        Long instructorId = null;
        String instructorName = null;
        if (course.getInstructor() != null) {
            instructorId = course.getInstructor().getId();
            instructorName = course.getInstructor().getName();
        }
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getCredit(),
                instructorId,
                instructorName
        );
    }
}