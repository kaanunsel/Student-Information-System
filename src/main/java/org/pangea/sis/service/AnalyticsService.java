package org.pangea.sis.service;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.repository.CourseRepository;
import org.pangea.sis.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for analytics-related operations.
 * Provides course performance summaries based on enrollment data.
 */
@Service
public class AnalyticsService {

    private final EnrollmentRepository enrollmentRepository;

    /**
     * Constructor-based injection for EnrollmentRepository.
     *
     * @param enrollmentRepository repository used to fetch enrollment data
     */
    @Autowired
    public AnalyticsService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Retrieves course performance statistics including average, min, max grades,
     * and student counts per course.
     *
     * @return list of course performance summaries
     */
    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return enrollmentRepository.getCoursePerformanceSummary();
    }
}