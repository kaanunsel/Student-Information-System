package org.pangea.sis.service;

import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.dto.EnrollmentMapper;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing enrollment operations between students and courses.
 */
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    /**
     * Constructor-based injection for EnrollmentRepository.
     *
     * @param enrollmentRepository repository for accessing enrollment data
     */
    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Retrieves all enrollment records in the system.
     *
     * @return list of all enrollments
     */
    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

    /**
     * Retrieves all enrollments associated with a given student ID.
     *
     * @param id student ID
     * @return list of enrollments for that student
     */
    public List<Enrollment> getByStudentId(Long id){
        return enrollmentRepository.findByStudentId(id);
    }

    /**
     * Retrieves all enrollments associated with a given course ID.
     *
     * @param id course ID
     * @return list of enrollments for that course
     */
    public List<Enrollment> getByCourseId(Long id){
        return enrollmentRepository.findByCourseId(id);
    }


    /**
     * Creates a new enrollment and sets the current timestamp.
     *
     * @param enrollment enrollment entity to save
     * @return saved enrollment
     */
    public Enrollment createEnrollment(Enrollment enrollment){
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Updates the grade for an existing enrollment.
     *
     * @param id enrollment ID
     * @param grade new grade value
     * @return updated enrollment as DTO
     * @throws RuntimeException if the enrollment is not found
     */
    public EnrollmentDTO updateGrade(Long id, Integer grade){
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toDTO(enrollment);
    }

    /**
     * Deletes an enrollment by its ID.
     *
     * @param id enrollment ID to delete
     */
    public void deleteEnrollment(Long id){
        enrollmentRepository.deleteById(id);
    }
}