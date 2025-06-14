package org.pangea.sis.service;

import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling instructor-related operations.
 */
@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    /**
     * Constructor-based injection for InstructorRepository.
     *
     * @param instructorRepository repository for accessing instructor data
     */
    @Autowired
    public InstructorService(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    /**
     * Retrieves an instructor by their ID.
     *
     * @param id instructor ID
     * @return optional containing the instructor if found
     */
    public Optional<Instructor> getInstructorById(Long id){
        return instructorRepository.findById(id);
    }

    public List<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }

    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    public Optional<Instructor> updateInstructor(Long id, Instructor instructor) {
        return instructorRepository.findById(id)
                .map(existingInstructor -> {
                    existingInstructor.setName(instructor.getName());
                    existingInstructor.setSurname(instructor.getSurname());
                    existingInstructor.setEmail(instructor.getEmail());
                    existingInstructor.setPassword(instructor.getPassword());
                    return instructorRepository.save(existingInstructor);
                });
    }
    
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}