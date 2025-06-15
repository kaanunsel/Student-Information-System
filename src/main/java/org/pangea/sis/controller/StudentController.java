package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.dto.StudentDTO;
import org.pangea.sis.dto.StudentMapper;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.entity.Student;
import org.pangea.sis.service.InstructorService;
import org.pangea.sis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing student-related endpoints.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final InstructorService instructorService;

    /**
     * Constructor for injecting student and instructor services.
     *
     * @param studentService    Service for student-related operations.
     * @param instructorService Service for instructor-related operations.
     */
    @Autowired
    public StudentController(StudentService studentService, InstructorService instructorService){
        this.studentService = studentService;
        this.instructorService = instructorService;
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
    ){
        if (id != null) {
            return studentService.getStudentById(id).stream().map(StudentMapper::toDto).toList();
        }
        else if (name != null && surname != null) {
            return studentService.getStudentsByNameAndSurname(name, surname).stream().map(StudentMapper::toDto).toList();
        }
        else if (name != null) {
            return studentService.getStudentsByName(name).stream().map(StudentMapper::toDto).toList();
        }
        else if (surname != null) {
            return studentService.getStudentsBySurname(surname).stream().map(StudentMapper::toDto).toList();
        }
        else{
            return studentService.getAllStudents().stream().map(StudentMapper::toDto).toList();
        }
    }

    /**
     * Adds a new student with an advisor.
     *
     * @param dto StudentDTO containing student data
     * @return created student as DTO or 404 if advisor not found
     */
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentDTO dto){
        Optional<Instructor> addedAdvisor = instructorService.getInstructorById(dto.getAdvisorId());
        if(addedAdvisor.isEmpty()){
            return new ResponseEntity<String>("No such advisor", HttpStatus.NOT_FOUND);
        }
        Student savedStudent = studentService.addStudent(StudentMapper.toEntity(dto, addedAdvisor.get()));
        return new ResponseEntity<>(StudentMapper.toDto(savedStudent), HttpStatus.CREATED);
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
        Instructor instructor = instructorService.getInstructorById(dto.getAdvisorId()).get();
        return studentService.updateStudent(id, StudentMapper.toEntity(dto, instructor))
                .map(updatedStudent -> new ResponseEntity<>(StudentMapper.toDto(updatedStudent), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a student by ID.
     *
     * @param id ID of the student to delete
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted.", HttpStatus.OK);
    }
}