package org.pangea.sis.controller;

import org.pangea.sis.entity.Student;
import org.pangea.sis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname
    ){
        if (id != null) {
        return studentService.getStudentById(id);
        }
        else if (name != null) {
            return studentService.getStudentsByName(name);
        }
        else if (surname != null) {
            return studentService.getStudentsBySurname(surname);
        }
        else{
            return studentService.getAllStudents();
        }
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(Student student){
        Student addedStudent = studentService.addStudent(student);
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Player deleted.", HttpStatus.OK);
    }
}
