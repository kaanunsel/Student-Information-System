package org.pangea.sis.service;

import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public Optional<Instructor> getInstructorById(Long id){
        return instructorRepository.findById(id);
    }
}
