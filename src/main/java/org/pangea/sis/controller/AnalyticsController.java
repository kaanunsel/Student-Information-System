package org.pangea.sis.controller;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.service.AnalyticsService;
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

    private final AnalyticsService analyticsService;

    /**
     * Constructor for injecting AnalyticsService.
     *
     * @param analyticsService service layer for analytics operations
     */
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Returns a list of course performance summaries, including average, min, and max grades.
     *
     * @return list of CoursePerformanceDTO
     */
    @GetMapping("/performance")
    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return analyticsService.getCoursePerformanceSummary();
    }

}