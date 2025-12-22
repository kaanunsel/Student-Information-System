package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Student;
import org.pangea.sis.domain.port.in.StudentUseCase;
import org.pangea.sis.domain.port.out.StudentRepositoryPort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing student use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
public class StudentService implements StudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    public StudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepositoryPort.findAll();
    }

    @Override
    public List<Student> getStudentById(Long id) {
        return studentRepositoryPort.findById(id).stream().toList();
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepositoryPort.findByNameContaining(name);
    }

    @Override
    public List<Student> getStudentsBySurname(String surname) {
        return studentRepositoryPort.findBySurnameContaining(surname);
    }

    @Override
    public List<Student> getStudentsByNameAndSurname(String name, String surname) {
        return studentRepositoryPort.findByNameAndSurnameContaining(name, surname);
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public Student addStudent(Student student) {
        student.setCreatedAt(LocalDateTime.now());
        return studentRepositoryPort.save(student);
    }

    @Override
    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepositoryPort.findById(id);

        if (optionalStudent.isEmpty()) {
            return Optional.empty();
        }

        Student existingStudent = optionalStudent.get();

        if (updatedStudent.getName() != null) {
            existingStudent.setName(updatedStudent.getName());
        }

        if (updatedStudent.getSurname() != null) {
            existingStudent.setSurname(updatedStudent.getSurname());
        }

        if (updatedStudent.getEmail() != null) {
            existingStudent.setEmail(updatedStudent.getEmail());
        }

        if (updatedStudent.getBirthDate() != null) {
            existingStudent.setBirthDate(updatedStudent.getBirthDate());
        }

        if (updatedStudent.getAdvisorId() != null) {
            existingStudent.setAdvisorId(updatedStudent.getAdvisorId());
        }

        Student savedStudent = studentRepositoryPort.save(existingStudent);
        return Optional.of(savedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepositoryPort.deleteById(id);
    }
}
