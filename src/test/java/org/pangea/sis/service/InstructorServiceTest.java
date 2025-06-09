package org.pangea.sis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.InstructorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    private InstructorRepository instructorRepository;
    private InstructorService instructorService;

    @BeforeEach
    void setUp() {
        instructorRepository = mock(InstructorRepository.class);
        instructorService = new InstructorService(instructorRepository);
    }

    @Test
    void testGetInstructorById_found() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John");
        instructor.setSurname("Doe");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        // Act
        Optional<Instructor> result = instructorService.getInstructorById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getSurname());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetInstructorById_notFound() {
        // Arrange
        when(instructorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Instructor> result = instructorService.getInstructorById(99L);

        // Assert
        assertTrue(result.isEmpty());
        verify(instructorRepository, times(1)).findById(99L);
    }
}