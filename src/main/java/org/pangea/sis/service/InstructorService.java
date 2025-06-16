package org.pangea.sis.service;

import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.entity.Student;
import org.pangea.sis.repository.CourseRepository;
import org.pangea.sis.repository.InstructorRepository;
import org.pangea.sis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Constructor-based injection for InstructorRepository.
     *
     * @param instructorRepository repository for accessing instructor data
     * @param courseRepository
     * @param studentRepository
     */
    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository, StudentRepository studentRepository){
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
     * Retrieves all instructors from the database.
     *
     * @return A list of all instructors.
     */
    public List<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }

    /**
     * Adds a new instructor to the database.
     *
     * @param instructor The instructor entity to add.
     * @return The saved instructor entity.
     */
    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    /**
     * Updates an existing instructor's information.
     *
     * @param id The ID of the instructor to update.
     * @param instructor The instructor entity with updated information.
     * @return An Optional containing the updated instructor, or empty if not found.
     */
    public Optional<Instructor> updateInstructor(Long id, Instructor instructor) {
        return instructorRepository.findById(id)
                .map(existingInstructor -> {
                    existingInstructor.setName(instructor.getName());
                    existingInstructor.setSurname(instructor.getSurname());
                    existingInstructor.setEmail(instructor.getEmail());
                    existingInstructor.setPassword(instructor.getPassword());
                    return instructorRepository.save(existingInstructor);
                });
    }
    
    @Transactional
    public void deleteInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));

        List<Course> courses = instructor.getCourses();
        for (Course course : courses) {
            course.setInstructor(null);
        }
        courseRepository.saveAll(courses);

        List<Student> students = instructor.getAdvisedStudents();
        for (Student student : students) {
            student.setAdvisor(null);
        }
        studentRepository.saveAll(students);

        instructorRepository.deleteById(id);
    }
    
    /**
     * Deletes an instructor from the database.
     *
     * @param id The ID of the instructor to delete.
     */
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}