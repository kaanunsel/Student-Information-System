package org.pangea.sis.application.service;

import org.pangea.sis.application.dto.CoursePerformanceDTO;
import org.pangea.sis.domain.port.in.AnalyticsUseCase;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.EnrollmentJpaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementing analytics use cases.
 * Note: This service directly accesses JPA repository for the specialized query,
 * as the query returns DTOs rather than domain models.
 */
@Service
public class AnalyticsService implements AnalyticsUseCase {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    public AnalyticsService(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
    }

    @Override
    @Cacheable(value = "analytics")
    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return enrollmentJpaRepository.getCoursePerformanceSummary();
    }
}
