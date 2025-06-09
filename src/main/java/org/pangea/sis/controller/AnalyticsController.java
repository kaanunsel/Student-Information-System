package org.pangea.sis.controller;

import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/performance")
    public List<CoursePerformanceDTO> getCoursePerformanceSummary() {
        return analyticsService.getCoursePerformanceSummary();
    }

}
