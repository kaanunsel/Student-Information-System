package org.pangea.sis.infrastructure.adapter.in.web.mapper;

import org.pangea.sis.domain.model.Student;
import org.pangea.sis.infrastructure.adapter.in.web.dto.StudentDTO;

/**
 * Mapper for converting between Student domain model and StudentDTO.
 * Handles transformations between web layer and domain layer.
 */
public class StudentWebMapper {

    /**
     * Converts a StudentDTO to a Student domain model.
     *
     * @param dto the StudentDTO containing student data
     * @return Student domain model
     */
    public static Student toDomain(StudentDTO dto) {
        Student domain = new Student();
        domain.setId(dto.getStudentId());
        domain.setName(dto.getName());
        domain.setSurname(dto.getSurname());
        domain.setEmail(dto.getEmail());
        domain.setBirthDate(dto.getBirthDate());
        domain.setAdvisorId(dto.getAdvisorId());
        return domain;
    }

    /**
     * Converts a Student domain model to a StudentDTO.
     *
     * @param student the Student domain model to convert
     * @return StudentDTO containing all necessary fields
     */
    public static StudentDTO toDto(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getBirthDate(),
                student.getAdvisorId(),
                null // advisorName can be populated separately if needed
        );
    }
}
