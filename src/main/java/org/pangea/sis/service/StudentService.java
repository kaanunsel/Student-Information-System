package org.pangea.sis.service;

import org.pangea.sis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.pangea.sis.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing student-related operations.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructor-based dependency injection for StudentRepository.
     *
     * @param studentRepository repository to handle student persistence
     */
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all students from the database.
     *
     * @return list of all students
     */
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by ID as a single-element list.
     *
     * @param id student ID
     * @return list with the matching student if found
     */
    public List<Student> getStudentById(Long id){
        return studentRepository.findById(id).stream().toList();
    }

    /**
     * Retrieves students by exact name match.
     *
     * @param name student's first name
     * @return list of matching students
     */
    public List<Student> getStudentsByName(String name){
        return studentRepository.findAllByName(name).stream().toList();
    }

    /**
     * Retrieves students by exact surname match.
     *
     * @param surname student's last name
     * @return list of matching students
     */
    public List<Student> getStudentsBySurname(String surname){
        return studentRepository.findAllBySurname(surname).stream().toList();
    }

    /**
     * Saves a new student with current timestamp.
     *
     * @param student student entity to save
     * @return saved student
     */
    public Student addStudent(Student student){
        student.setCreatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    /**
     * Deletes a student by ID.
     *
     * @param id ID of the student to delete
     */
    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    /**
     * Updates an existing student's information.
     *
     * @param id ID of the student to update
     * @param updatedStudent object with updated data
     * @return updated student wrapped in Optional, or empty if not found
     */
    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setSurname(updatedStudent.getSurname());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setBirthDate(updatedStudent.getBirthDate());

            Student savedStudent = studentRepository.save(existingStudent);
            return Optional.of(savedStudent);
        } else {
            return Optional.empty();
        }
    }
}