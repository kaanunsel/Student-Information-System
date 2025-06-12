package org.pangea.sis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.repository.EnrollmentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void getAllEnrollments() {
        // Given
        Enrollment e1 = new Enrollment();
        Enrollment e2 = new Enrollment();
        List<Enrollment> enrollments = Arrays.asList(e1, e2);
        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        // When
        List<Enrollment> result = enrollmentService.getAllEnrollments();

        // Then
        assertEquals(2, result.size());
        assertSame(enrollments, result);
        verify(enrollmentRepository).findAll();
    }

    @Test
    void getByStudentId() {
        // Given
        Long studentId = 1L;
        Enrollment e = new Enrollment();
        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Collections.singletonList(e));

        // When
        List<Enrollment> result = enrollmentService.getByStudentId(studentId);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(e));
        verify(enrollmentRepository).findByStudentId(studentId);
    }

    @Test
    void getByCourseId() {
        // Given
        Long courseId = 2L;
        Enrollment e = new Enrollment();
        when(enrollmentRepository.findByCourseId(courseId)).thenReturn(Collections.singletonList(e));

        // When
        List<Enrollment> result = enrollmentService.getByCourseId(courseId);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(e));
        verify(enrollmentRepository).findByCourseId(courseId);
    }

    @Test
    void createEnrollment() {
        // Given
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Enrollment result = enrollmentService.createEnrollment(enrollment);

        // Then
        assertNotNull(result.getEnrolledAt());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void updateGrade() {
        // Given
        Long enrollmentId = 3L;
        Integer newGrade = 85;
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        enrollment.setGrade(70);
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        EnrollmentDTO dto = enrollmentService.updateGrade(enrollmentId, newGrade);

        // Then
        assertEquals(newGrade, dto.getGrade());
        assertEquals(enrollmentId, dto.getId());
        verify(enrollmentRepository).findById(enrollmentId);
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void deleteEnrollment() {
        // Given
        Long enrollmentId = 5L;

        // When
        enrollmentService.deleteEnrollment(enrollmentId);

        // Then
        verify(enrollmentRepository).deleteById(enrollmentId);
    }
}