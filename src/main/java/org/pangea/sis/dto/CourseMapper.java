package org.pangea.sis.dto;

import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;

public class CourseMapper {
    public static Course toEntity(CourseDTO dto, Instructor instructor){
        Course entity = new Course();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCredit(dto.getCredit());
        entity.setInstructor(instructor);
        return entity;
    }

    public static CourseDTO toDto(Course course){
        return new CourseDTO(
                course.getName(),
                course.getCode(),
                course.getCredit(),
                course.getInstructor().getId(),
                course.getInstructor().getName()
        );
    }
}
