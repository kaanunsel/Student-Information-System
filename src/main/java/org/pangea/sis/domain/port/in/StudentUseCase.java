package org.pangea.sis.domain.port.in;

import org.pangea.sis.domain.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * Input port (use case interface) for student operations.
 * Defines business operations available for students.
 */
public interface StudentUseCase {
    List<Student> getAllStudents();
    List<Student> getStudentById(Long id);
    List<Student> getStudentsByName(String name);
    List<Student> getStudentsBySurname(String surname);
    List<Student> getStudentsByNameAndSurname(String name, String surname);
    Student addStudent(Student student);
    Optional<Student> updateStudent(Long id, Student updatedStudent);
    void deleteStudent(Long id);
}
