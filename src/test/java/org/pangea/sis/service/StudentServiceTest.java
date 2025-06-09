package org.pangea.sis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Student;
import org.pangea.sis.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    private StudentRepository studentRepository;
    private StudentService studentService;

    // Set up mock repository and service before each test
    @BeforeEach
    void setup() {
        studentRepository = mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
    }

    // Test adding a new student
    @Test
    void testAddStudent() {
        Student student = new Student();
        student.setName("Kaan");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.addStudent(student);

        assertEquals("Kaan", result.getName());
        verify(studentRepository).save(student);
    }

    // Test retrieving a student by ID when they exist
    @Test
    void shouldReturnStudentWhenIdExists() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Kaan");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        List<Student> result = studentService.getStudentById(1L);

        assertEquals(1, result.size());
        assertEquals("Kaan", result.getFirst().getName());
        verify(studentRepository).findById(1L);
    }

    // Test retrieving a student by ID when they don't exist
    @Test
    void shouldReturnEmptyListWhenStudentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        List<Student> result = studentService.getStudentById(99L);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(99L);
    }

    // Test finding students by their name
    @Test
    void shouldReturnStudentsByName() {
        Student s1 = new Student();
        s1.setName("Gizem");

        when(studentRepository.findAllByName("Gizem")).thenReturn(List.of(s1));

        List<Student> result = studentService.getStudentsByName("Gizem");

        assertEquals(1, result.size());
        assertEquals("Gizem", result.getFirst().getName());
        verify(studentRepository).findAllByName("Gizem");
    }

    // Test finding students by their surname
    @Test
    void shouldReturnStudentsBySurname() {
        Student s1 = new Student();
        s1.setSurname("Şenel");

        when(studentRepository.findAllBySurname("Şenel")).thenReturn(List.of(s1));

        List<Student> result = studentService.getStudentsBySurname("Şenel");

        assertEquals(1, result.size());
        assertEquals("Şenel", result.getFirst().getSurname());
        verify(studentRepository).findAllBySurname("Şenel");
    }

    // Test deleting a student by ID
    @Test
    void shouldDeleteStudentById() {
        studentService.deleteStudent(5L);
        verify(studentRepository, times(1)).deleteById(5L);
    }

    // Test updating an existing student's information
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

        Optional<Student> result = studentService.updateStudent(10L, updated);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
        assertEquals("new@example.com", result.get().getEmail());
        verify(studentRepository).findById(10L);
        verify(studentRepository).save(any(Student.class));
    }

    // Test updating a student that does not exist
    @Test
    void shouldReturnEmptyWhenUpdatingNonexistentStudent() {
        Student updated = new Student();
        updated.setName("Ghost");

        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.updateStudent(99L, updated);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(99L);
        verify(studentRepository, never()).save(any(Student.class));
    }
}