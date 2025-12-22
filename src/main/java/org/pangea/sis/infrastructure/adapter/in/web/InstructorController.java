package org.pangea.sis.infrastructure.adapter.in.web;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.domain.port.in.InstructorUseCase;
import org.pangea.sis.infrastructure.adapter.in.web.dto.InstructorDTO;
import org.pangea.sis.infrastructure.adapter.in.web.mapper.InstructorWebMapper;
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
    public List<InstructorDTO> getAllInstructors() {
        return instructorUseCase.getAllInstructors().stream()
                .map(InstructorWebMapper::toDto)
                .toList();
    }

    /**
     * Adds a new instructor to the system.
     *
     * @param dto The instructor dto to add.
     * @return The saved instructor with a CREATED status.
     */
    @PostMapping
    public ResponseEntity<InstructorDTO> addInstructor(@RequestBody @jakarta.validation.Valid InstructorDTO dto) {
        Instructor instructor = InstructorWebMapper.toDomain(dto);
        Instructor savedInstructor = instructorUseCase.addInstructor(instructor);
        return new ResponseEntity<>(InstructorWebMapper.toDto(savedInstructor), HttpStatus.CREATED);
    }

    /**
     * Updates an existing instructor.
     *
     * @param id  The ID of the instructor to update.
     * @param dto The instructor data to update.
     * @return The updated instructor or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable Long id,
            @RequestBody @jakarta.validation.Valid InstructorDTO dto) {
        Instructor instructor = InstructorWebMapper.toDomain(dto);
        return instructorUseCase.updateInstructor(id, instructor)
                .map(updatedInstructor -> new ResponseEntity<>(InstructorWebMapper.toDto(updatedInstructor),
                        HttpStatus.OK))
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
        // Here we rely on the service or check existence first.
        // For consistency with other controllers, we can call service.
        // Ideally service throws exception if not found, but we will check mapping.

        Optional<Instructor> instructor = instructorUseCase.getInstructorById(id);
        if (instructor.isPresent()) {
            instructorUseCase.deleteInstructor(id);
            return new ResponseEntity<>("Instructor deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No such instructor", HttpStatus.NOT_FOUND);
        }
    }
}
