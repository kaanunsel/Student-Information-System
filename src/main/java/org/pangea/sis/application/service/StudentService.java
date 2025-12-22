package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Student;
import org.pangea.sis.domain.port.in.StudentUseCase;
import org.pangea.sis.domain.port.out.StudentRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing student use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
@Transactional(readOnly = true)
public class StudentService implements StudentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepositoryPort studentRepositoryPort;

    public StudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
    }

    @Override
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRepositoryPort.findAll();
    }

    @Override
    public List<Student> getStudentById(Long id) {
        logger.info("Fetching student by id: {}", id);
        return studentRepositoryPort.findById(id).stream().toList();
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        logger.info("Fetching students with name containing: {}", name);
        return studentRepositoryPort.findByNameContaining(name);
    }

    @Override
    public List<Student> getStudentsBySurname(String surname) {
        logger.info("Fetching students with surname containing: {}", surname);
        return studentRepositoryPort.findBySurnameContaining(surname);
    }

    @Override
    public List<Student> getStudentsByNameAndSurname(String name, String surname) {
        logger.info("Fetching students with name containing: {} and surname containing: {}", name, surname);
        return studentRepositoryPort.findByNameAndSurnameContaining(name, surname);
    }

    @Override
    @Transactional
    @CacheEvict(value = "students", allEntries = true)
    public Student addStudent(Student student) {
        logger.info("Adding new student: {} {}", student.getName(), student.getSurname());
        student.setCreatedAt(LocalDateTime.now());
        return studentRepositoryPort.save(student);
    }

    @Override
    @Transactional
    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        logger.info("Updating student with id: {}", id);
        Optional<Student> optionalStudent = studentRepositoryPort.findById(id);

        if (optionalStudent.isEmpty()) {
            logger.warn("Student with id: {} not found for update", id);
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
    @Transactional
    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: {}", id);
        studentRepositoryPort.deleteById(id);
    }
}
