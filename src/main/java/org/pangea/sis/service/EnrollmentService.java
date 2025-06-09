package org.pangea.sis.service;

import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.dto.EnrollmentMapper;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getByStudentId(Long id){
        return enrollmentRepository.findByStudentId(id);
    }

    public Enrollment createEnrollment(Enrollment enrollment){
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }
    public EnrollmentDTO updateGrade(Long id, Integer grade){
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setGrade(grade);
        return EnrollmentMapper.toDTO(enrollment);
    }

    public void deleteEnrollment(Long id){
        enrollmentRepository.deleteById(id);
    }
}
