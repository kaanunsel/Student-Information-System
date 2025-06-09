package org.pangea.sis.dto;

import org.pangea.sis.entity.Instructor;
import org.pangea.sis.entity.Student;

/**
 * Mapper class for converting between Student entity and StudentDTO.
 * Handles transformations including optional advisor information.
 */
public class StudentMapper {

    /**
     * Converts a StudentDTO to a Student entity (without advisor).
     *
     * @param dto the StudentDTO containing student data
     * @return Student entity with basic fields
     */
    public static Student toEntity(StudentDTO dto){
        Student entity = new Student();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        return entity;
    }

    /**
     * Converts a StudentDTO to a Student entity with an advisor.
     *
     * @param dto     the StudentDTO containing student data
     * @param advisor the Instructor entity to be set as advisor
     * @return Student entity with advisor assigned
     */
    public static Student toEntity(StudentDTO dto, Instructor advisor){
        Student entity = toEntity(dto);
        entity.setAdvisor(advisor);
        return entity;
    }

    /**
     * Converts a Student entity to a StudentDTO.
     *
     * @param student the Student entity to convert
     * @return StudentDTO containing all necessary fields including advisor info
     */
    public static StudentDTO toDto(Student student){
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getBirthDate(),
                student.getAdvisor().getId(),
                student.getAdvisor().getName()
        );
    }
}