package org.pangea.sis.service;

import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.InstructorRepository;
import org.pangea.sis.repository.CourseRepository;
import org.pangea.sis.repository.StudentRepository;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling instructor-related operations.
 */
@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    /**
     * Constructor-based injection for repositories used by this service.
     *
     * @param instructorRepository repository for accessing instructor data
     * @param courseRepository repository for course data
     * @param studentRepository repository for student data
     */
    @Autowired
    public InstructorService(
            InstructorRepository instructorRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository
    ){
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves an instructor by their ID.
     *
     * @param id instructor ID
     * @return optional containing the instructor if found
     */
    public Optional<Instructor> getInstructorById(Long id){
        return instructorRepository.findById(id);
    }

    /**
     * Returns all instructors in the system.
     */
    public List<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }

    /**
     * Saves a new instructor.
     */
    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    /**
     * Deletes an instructor by id after detaching related entities.
     * Courses taught by this instructor and students advised by them
     * will have their references cleared to maintain integrity.
     *
     * @param id instructor ID
     */
    public void deleteInstructor(Long id){
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if(optionalInstructor.isEmpty()){
            return;
        }
        Instructor instructor = optionalInstructor.get();

        for (Course course : courseRepository.findAllByInstructorId(id)) {
            course.setInstructor(null);
            courseRepository.save(course);
        }

        for (Student student : studentRepository.findAllByAdvisorId(id)) {
            student.setAdvisor(null);
            studentRepository.save(student);
        }

        instructorRepository.delete(instructor);
    }
}