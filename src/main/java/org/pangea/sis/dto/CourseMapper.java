package org.pangea.sis.dto;

import org.pangea.sis.entity.Course;

public class CourseMapper {
    public static Course toEntity(CourseDTO dto){
        Course entity = new Course();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCredit(dto.getCredit());
        return entity;
    }

    public static CourseDTO toDto(Course course){
        return new CourseDTO(
                course.getName(),
                course.getCode(),
                course.getCredit()
        );
    }
}
