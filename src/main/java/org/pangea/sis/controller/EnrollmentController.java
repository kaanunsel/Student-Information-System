package org.pangea.sis.controller;

import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.dto.EnrollmentMapper;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.service.EnrollmentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments(){
        return enrollmentService.getAllEnrollments().stream().map(EnrollmentMapper::toDTO).toList();
    }

    @GetMapping("/student")
    public List<EnrollmentDTO> getByStudentId(@RequestParam Long studentId){
        return enrollmentService.getByStudentId(studentId).stream().map(EnrollmentMapper::toDTO).toList();
    }

}
