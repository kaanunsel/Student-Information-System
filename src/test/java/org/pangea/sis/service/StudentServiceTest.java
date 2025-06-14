package org.pangea.sis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pangea.sis.entity.Student;
import org.pangea.sis.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link StudentService} class.
 * This class uses Mockito to mock the {@link StudentRepository} dependency
 * and tests the business logic of the StudentService layer independently.
 */
public class StudentServiceTest {


    @Mock
    private StudentRepository studentRepository;
    AutoCloseable autoCloseable;
    private StudentService underTest;

    /**
     * Initializes mock repository and service before each test case.
     */
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void shouldGetAllStudents(){
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }


    /**
     * Tests if a student can be successfully added using the service.
     */
    @Test
    void testAddStudent() {
        // given
        Student student = new Student();
        student.setName("Kaan");

        // when
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        assertEquals(studentArgumentCaptor.getValue(), student);

    }

    /**
     * Tests if the correct student is returned when an existing ID is given.
     */
    @Test
    void shouldReturnStudentWhenIdExists() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Kaan");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        List<Student> result = underTest.getStudentById(1L);

        assertEquals(1, result.size());
        assertEquals("Kaan", result.getFirst().getName());
        verify(studentRepository).findById(1L);
    }

    /**
     * Tests if an empty list is returned when a non-existent ID is used.
     */
    @Test
    void shouldReturnEmptyListWhenStudentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        List<Student> result = underTest.getStudentById(99L);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(99L);
    }

    /**
     * Tests if students can be found by name.
     */
    @Test
    void shouldReturnStudentsByName() {
        Student s1 = new Student();
        s1.setName("Gizem");

        when(studentRepository.findAllByNameContainingIgnoreCase("Gizem")).thenReturn(List.of(s1));

        List<Student> result = underTest.getStudentsByName("Gizem");

        assertEquals(1, result.size());
        assertEquals("Gizem", result.getFirst().getName());
        verify(studentRepository).findAllByNameContainingIgnoreCase("Gizem");
    }

    /**
     * Tests if students can be found by surname.
     */
    @Test
    void shouldReturnStudentsBySurname() {
        Student s1 = new Student();
        s1.setSurname("Şenel");

        when(studentRepository.findAllBySurnameContainingIgnoreCase("Şenel")).thenReturn(List.of(s1));

        List<Student> result = underTest.getStudentsBySurname("Şenel");

        assertEquals(1, result.size());
        assertEquals("Şenel", result.getFirst().getSurname());
        verify(studentRepository).findAllBySurnameContainingIgnoreCase("Şenel");
    }

    /**
     * Tests if delete operation correctly calls the repository.
     */
    @Test
    void shouldDeleteStudentById() {
        underTest.deleteStudent(5L);
        verify(studentRepository, times(1)).deleteById(5L);
    }

    /**
     * Tests updating an existing student's information.
     */
    @Test
    void shouldUpdateStudentWhenExists() {
        Student original = new Student();
        original.setId(10L);
        original.setName("Old");
        original.setSurname("Name");
        original.setEmail("old@example.com");
        original.setBirthDate(LocalDate.of(2000, 1, 1));

        Student updated = new Student();
        updated.setName("New");
        updated.setSurname("Surname");
        updated.setEmail("new@example.com");
        updated.setBirthDate(LocalDate.of(2001, 2, 2));

        when(studentRepository.findById(10L)).thenReturn(Optional.of(original));
        when(studentRepository.save(any(Student.class))).thenReturn(updated);

        Optional<Student> result = underTest.updateStudent(10L, updated);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
        assertEquals("new@example.com", result.get().getEmail());
        verify(studentRepository).findById(10L);
        verify(studentRepository).save(any(Student.class));
    }

    /**
     * Tests updating a non-existent student.
     */
    @Test
    void shouldReturnEmptyWhenUpdatingNonexistentStudent() {
        Student updated = new Student();
        updated.setName("Ghost");

        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Student> result = underTest.updateStudent(99L, updated);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(99L);
        verify(studentRepository, never()).save(any(Student.class));
    }


}