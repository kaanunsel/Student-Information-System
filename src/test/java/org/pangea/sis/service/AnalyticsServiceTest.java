package org.pangea.sis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pangea.sis.dto.CoursePerformanceDTO;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.repository.EnrollmentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    private AnalyticsService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AnalyticsService(enrollmentRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getCoursePerformanceSummary() {
        // given
        CoursePerformanceDTO dto1 = new CoursePerformanceDTO(1L, "Intro to CS", 93.0, 90L, 70, 100);
        CoursePerformanceDTO dto2 = new CoursePerformanceDTO(2L, "Calculus", 82.0, 75L, 60, 85);
        List<CoursePerformanceDTO> mockResult = List.of(dto1, dto2);

        when(enrollmentRepository.getCoursePerformanceSummary()).thenReturn(mockResult);

        // when
        List<CoursePerformanceDTO> result = underTest.getCoursePerformanceSummary();

        // then
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getCourseId());
        assertEquals("Calculus", result.get(1).getCourseName());
        assertEquals(90L, result.get(0).getNumberOfStudents());
        assertEquals(82.0, result.get(1).getAvgGrade());

        verify(enrollmentRepository, times(1)).getCoursePerformanceSummary();
    }
}