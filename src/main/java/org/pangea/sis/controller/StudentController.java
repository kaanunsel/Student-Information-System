package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.dto.StudentDTO;
import org.pangea.sis.dto.StudentMapper;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.entity.Student;
import org.pangea.sis.service.InstructorService;
import org.pangea.sis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final InstructorService instructorService;
    @Autowired
    public StudentController(StudentService studentService, InstructorService instructorService){
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<StudentDTO> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname
    ){
        if (id != null) {
        return studentService.getStudentById(id).stream().map(StudentMapper::toDto).toList();
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

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentDTO dto){
        Optional<Instructor> addedAdvisor = instructorService.getInstructorById(dto.getAdvisorId());
        if(addedAdvisor.isEmpty()){
            return new ResponseEntity<String>("No such advisor", HttpStatus.NOT_FOUND);
        }
        Student savedStudent = studentService.addStudent(StudentMapper.toEntity(dto, addedAdvisor.get()));
        return new ResponseEntity<>(StudentMapper.toDto(savedStudent), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
        @PathVariable Long id,
        @RequestBody @Valid StudentDTO dto
    ) {
    return studentService.updateStudent(id, StudentMapper.toEntity(dto))
            .map(updatedStudent -> new ResponseEntity<>(StudentMapper.toDto(updatedStudent), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Player deleted.", HttpStatus.OK);
    }
}
