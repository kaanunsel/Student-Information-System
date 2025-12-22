package org.pangea.sis.infrastructure.adapter.in.web.mapper;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.infrastructure.adapter.in.web.dto.InstructorDTO;

public class InstructorWebMapper {

    public static InstructorDTO toDto(Instructor instructor) {
        if (instructor == null) {
            return null;
        }
        return new InstructorDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getSurname(),
                instructor.getEmail(),
                instructor.getDepartment(),
                instructor.getHiringDate());
    }

    public static Instructor toDomain(InstructorDTO dto) {
        if (dto == null) {
            return null;
        }
        Instructor instructor = new Instructor();
        instructor.setId(dto.getId());
        instructor.setName(dto.getName());
        instructor.setSurname(dto.getSurname());
        instructor.setEmail(dto.getEmail());
        instructor.setDepartment(dto.getDepartment());
        instructor.setHiringDate(dto.getHiringDate());
        return instructor;
    }
}
