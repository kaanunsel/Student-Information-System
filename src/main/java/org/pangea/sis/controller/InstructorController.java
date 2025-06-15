package org.pangea.sis.controller;

import org.pangea.sis.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.pangea.sis.entity.Instructor;
import java.util.List;

/**
 * REST controller for managing instructor-related operations.
 */
@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    /**
     * Constructor for injecting the InstructorService.
     *
     * @param instructorService Service for instructor-related business logic.
     */
    @Autowired
    public InstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    /**
     * Retrieves all instructors in the system.
     *
     * @return A list of all instructors.
     */
    @GetMapping
    public List<Instructor> getAllInstructors(){
        return instructorService.getAllInstructors();
    }

    /**
     * Adds a new instructor to the system.
     *
     * @param instructor The instructor entity to add.
     * @return The saved instructor with a CREATED status.
     */
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor) {
        Instructor savedInstructor = instructorService.addInstructor(instructor);
        return new ResponseEntity<>(savedInstructor, HttpStatus.CREATED);
    }

    /**
     * Updates an existing instructor.
     *
     * @param id The ID of the instructor to update.
     * @param instructor The instructor data to update.
     * @return The updated instructor or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        return instructorService.updateInstructor(id, instructor)
                .map(updatedInstructor -> new ResponseEntity<>(updatedInstructor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes an instructor from the system.
     *
     * @param id The ID of the instructor to delete.
     * @return A confirmation message with an OK status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return new ResponseEntity<>("Instructor deleted.", HttpStatus.OK);
    }
}
