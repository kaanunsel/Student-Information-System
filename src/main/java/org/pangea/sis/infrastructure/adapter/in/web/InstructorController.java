package org.pangea.sis.infrastructure.adapter.in.web;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.domain.port.in.InstructorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing instructor-related operations.
 */
@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorUseCase instructorUseCase;

    public InstructorController(InstructorUseCase instructorUseCase) {
        this.instructorUseCase = instructorUseCase;
    }

    /**
     * Retrieves all instructors in the system.
     *
     * @return A list of all instructors.
     */
    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorUseCase.getAllInstructors();
    }

    /**
     * Adds a new instructor to the system.
     *
     * @param instructor The instructor entity to add.
     * @return The saved instructor with a CREATED status.
     */
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor) {
        Instructor savedInstructor = instructorUseCase.addInstructor(instructor);
        return new ResponseEntity<>(savedInstructor, HttpStatus.CREATED);
    }

    /**
     * Updates an existing instructor.
     *
     * @param id         The ID of the instructor to update.
     * @param instructor The instructor data to update.
     * @return The updated instructor or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        return instructorUseCase.updateInstructor(id, instructor)
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
        Optional<Instructor> instructor = instructorUseCase.getInstructorById(id);
        if (instructor.isPresent()) {
            instructorUseCase.deleteInstructor(id);
        } else {
            return new ResponseEntity<>("No such instructor", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Instructor deleted.", HttpStatus.OK);
    }
}
