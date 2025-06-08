package org.pangea.sis.dto;

import org.pangea.sis.entity.Student;

public class StudentMapper {
    public static Student toEntity(StudentDTO dto){
        Student entity = new Student();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        return entity;
    }

    public static StudentDTO toDto(Student student){
        return new StudentDTO(
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getBirthDate()
        );
    }
}
