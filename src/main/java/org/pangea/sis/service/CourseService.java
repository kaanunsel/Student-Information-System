package org.pangea.sis.service;

import org.pangea.sis.entity.Course;
import org.pangea.sis.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class that handles business logic related to Course operations.
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * Constructor-based dependency injection for CourseRepository.
     *
     * @param courseRepository repository for accessing course data
     */
    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    /**
     * Retrieves all courses in the system.
     *
     * @return list of all courses
     */
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    /**
     * Retrieves a course by its ID as a single-element list.
     *
     * @param id course ID
     * @return list containing the course, if found
     */
    public List<Course> getCourseById(Long id){
        return courseRepository.findById(id).stream().toList();
    }

    /**
     * Searches for courses by partial match of their name (case-insensitive).
     *
     * @param name partial or full course name
     * @return list of matching courses
     */
    public List<Course> getCoursesByName(String name){
        return courseRepository.findAllByNameContainingIgnoreCase(name);
    }

    /**
     * Searches for courses by partial match of their code (case-insensitive).
     *
     * @param code partial or full course code
     * @return list of matching courses
     */
    public List<Course> getCoursesByCode(String code){
        return courseRepository.findAllByCodeContainingIgnoreCase(code);
    }

    /**
     * Searches for courses by their instructors' ids.
     *
     * @param instructorId instructor's id
     * @return list of matching courses
     */
    public List<Course> getCoursesByInstructorId(Long instructorId){
        return courseRepository.findAllByInstructorId(instructorId);
    }

    /**
     * Adds a new course with the current timestamp.
     *
     * @param course course entity to add
     * @return saved course
     */
    public Course addCourse(Course course){
        course.setCreatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID.
     *
     * @param id course ID to delete
     */
    public void deleteCourseById(Long id){
        courseRepository.deleteById(id);
    }

    /**
     * Deletes a course by its code.
     *
     * @param code course code to delete
     */
    public void deleteCourseByCode(String code) {
        courseRepository.deleteAllByCode(code);
    }

    /**
     * Updates an existing course by its code.
     *
     * @param code code of the course to update
     * @param updatedCourse course data to update with
     * @return updated course wrapped in Optional, or empty if not found
     */
    public Optional<Course> updateCourse(String code, Course updatedCourse) {
        Optional<Course> optionalCourse = courseRepository.findByCode(code);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setCode(updatedCourse.getCode());
            existingCourse.setCredit(updatedCourse.getCredit());
            existingCourse.setInstructor(updatedCourse.getInstructor());
            Course saved = courseRepository.save(existingCourse);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }
}