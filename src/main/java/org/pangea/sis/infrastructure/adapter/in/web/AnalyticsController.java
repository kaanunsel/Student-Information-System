package org.pangea.sis.infrastructure.adapter.in.web;

import org.pangea.sis.application.dto.CoursePerformanceDTO;
import org.pangea.sis.domain.port.in.AnalyticsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for analytics-related endpoints.
 * Provides insights such as course performance summaries.
 */
@RestController
@RequestMapping("analytics")
public class AnalyticsController {

    private final AnalyticsUseCase analyticsUseCase;

    public AnalyticsController(AnalyticsUseCase analyticsUseCase) {
        this.analyticsUseCase = analyticsUseCase;
    }

    /**
     * Returns a list of course performance summaries, including average, min, and max grades.
     *
     * @return list of CoursePerformanceDTO
     */
    @GetMapping("/performance")
    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return analyticsUseCase.getCoursePerformanceSummary();
    }
}
