package org.pangea.sis.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.pangea.sis.domain.model.Student;
import org.pangea.sis.domain.port.in.StudentUseCase;
import org.pangea.sis.infrastructure.adapter.in.web.dto.StudentDTO;
import org.pangea.sis.infrastructure.adapter.in.web.mapper.StudentWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing student-related endpoints.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentUseCase studentUseCase;

    public StudentController(StudentUseCase studentUseCase) {
        this.studentUseCase = studentUseCase;
    }

    /**
     * Retrieves students filtered by optional parameters: ID, name, or surname.
     * Returns all students if no filter is provided.
     *
     * @param id      optional student ID
     * @param name    optional student name
     * @param surname optional student surname
     * @return list of matching StudentDTOs
     */
    @GetMapping
    public List<StudentDTO> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname
    ) {
        if (id != null) {
            return studentUseCase.getStudentById(id).stream()
                    .map(StudentWebMapper::toDto)
                    .toList();
        } else if (name != null && surname != null) {
            return studentUseCase.getStudentsByNameAndSurname(name, surname).stream()
                    .map(StudentWebMapper::toDto)
                    .toList();
        } else if (name != null) {
            return studentUseCase.getStudentsByName(name).stream()
                    .map(StudentWebMapper::toDto)
                    .toList();
        } else if (surname != null) {
            return studentUseCase.getStudentsBySurname(surname).stream()
                    .map(StudentWebMapper::toDto)
                    .toList();
        } else {
            return studentUseCase.getAllStudents().stream()
                    .map(StudentWebMapper::toDto)
                    .toList();
        }
    }

    /**
     * Adds a new student with an advisor.
     *
     * @param dto StudentDTO containing student data
     * @return created student as DTO or 404 if advisor not found
     */
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentDTO dto) {
        Student student = StudentWebMapper.toDomain(dto);
        Student savedStudent = studentUseCase.addStudent(student);
        return new ResponseEntity<>(StudentWebMapper.toDto(savedStudent), HttpStatus.CREATED);
    }

    /**
     * Updates an existing student's information.
     *
     * @param id  ID of the student to update
     * @param dto updated student data
     * @return updated StudentDTO or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid StudentDTO dto
    ) {
        Student student = StudentWebMapper.toDomain(dto);
        return studentUseCase.updateStudent(id, student)
                .map(updatedStudent -> new ResponseEntity<>(StudentWebMapper.toDto(updatedStudent), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a student by ID.
     *
     * @param id ID of the student to delete
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentUseCase.deleteStudent(id);
        return new ResponseEntity<>("Student deleted.", HttpStatus.OK);
    }
}
