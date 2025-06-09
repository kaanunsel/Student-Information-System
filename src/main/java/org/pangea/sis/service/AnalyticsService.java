package org.pangea.sis.service;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.repository.CourseRepository;
import org.pangea.sis.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public AnalyticsService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return enrollmentRepository.getCoursePerformanceSummary();
    }
}
