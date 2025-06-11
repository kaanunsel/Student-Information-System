package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for instructor management.
 */
@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    /**
     * Returns all instructors.
     */
    @GetMapping
    public List<Instructor> getAll(){
        return instructorService.getAllInstructors();
    }

    /**
     * Creates a new instructor.
     */
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody @Valid Instructor instructor){
        Instructor saved = instructorService.addInstructor(instructor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Deletes an instructor by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id){
        instructorService.deleteInstructor(id);
        return new ResponseEntity<>("Instructor deleted", HttpStatus.OK);
    }
}

