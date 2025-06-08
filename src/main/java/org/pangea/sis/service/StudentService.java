package org.pangea.sis.service;

import org.pangea.sis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.pangea.sis.entity.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public List<Student> getStudentById(Long id){
        return studentRepository.findById(id).stream().toList();
    }

    public List<Student> getStudentsByName(String name){
        return studentRepository.findAllByName(name).stream().toList();
    }

    public List<Student> getStudentsBySurname(String surname){
        return studentRepository.findAllBySurname(surname).stream().toList();
    }

    public Student addStudent(Student student){
        student.setCreatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

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
