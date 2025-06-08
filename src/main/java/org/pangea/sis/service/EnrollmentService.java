package org.pangea.sis.service;

import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.dto.EnrollmentMapper;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.repository.EnrollmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }


    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getByStudentId(Long id){
        return enrollmentRepository.findByStudentId(id);
    }

}
