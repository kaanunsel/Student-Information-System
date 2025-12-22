package org.pangea.sis.domain.port.in;

import org.pangea.sis.application.dto.CoursePerformanceDTO;

import java.util.List;

/**
 * Input port (use case interface) for analytics operations.
 * Defines business operations for retrieving course performance analytics.
 */
public interface AnalyticsUseCase {
    List<CoursePerformanceDTO> getCoursePerformanceSummary();
}
