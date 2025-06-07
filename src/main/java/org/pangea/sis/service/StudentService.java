package org.pangea.sis.service;

import org.pangea.sis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.pangea.sis.entity.Student;
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

    public Optional<Student> getStudentById(Long id){
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentsByName(String name){
        return studentRepository.findAllByName(name);
    }
    public Optional<Student> getStudentsBySurname(String surname){
        return studentRepository.findAllBySurname(surname);
    }
    public Optional<Student> getStudentsByEmail(String email){
        return studentRepository.findAllByEmail(email);
    }
}
