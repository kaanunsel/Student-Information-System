package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Course;
import org.pangea.sis.domain.port.in.CourseUseCase;
import org.pangea.sis.domain.port.out.CourseRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing course use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
@Transactional(readOnly = true)
public class CourseService implements CourseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepositoryPort courseRepositoryPort;

    public CourseService(CourseRepositoryPort courseRepositoryPort) {
        this.courseRepositoryPort = courseRepositoryPort;
    }

    @Override
    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepositoryPort.findAll();
    }

    @Override
    public List<Course> getCourseById(Long id) {
        logger.info("Fetching course by id: {}", id);
        return courseRepositoryPort.findById(id).stream().toList();
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        logger.info("Fetching courses with name containing: {}", name);
        return courseRepositoryPort.findByNameContaining(name);
    }

    @Override
    public List<Course> getCoursesByCode(String code) {
        logger.info("Fetching courses with code containing: {}", code);
        return courseRepositoryPort.findByCodeContaining(code);
    }

    @Override
    public List<Course> getCoursesByInstructorId(Long instructorId) {
        logger.info("Fetching courses for instructor id: {}", instructorId);
        return courseRepositoryPort.findByInstructorId(instructorId);
    }

    @Override
    @Transactional
    public Course addCourse(Course course) {
        logger.info("Adding new course: {}", course.getName());
        course.setCreatedAt(LocalDateTime.now());
        return courseRepositoryPort.save(course);
    }

    @Override
    @Transactional
    public Optional<Course> updateCourse(String code, Course updatedCourse) {
        logger.info("Updating course with code: {}", code);
        Optional<Course> optionalCourse = courseRepositoryPort.findByCode(code);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setCode(updatedCourse.getCode());
            existingCourse.setCredit(updatedCourse.getCredit());
            existingCourse.setInstructorId(updatedCourse.getInstructorId());
            Course saved = courseRepositoryPort.save(existingCourse);
            return Optional.of(saved);
        } else {
            logger.warn("Course with code: {} not found for update", code);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        logger.info("Deleting course by id: {}", id);
        courseRepositoryPort.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseByCode(String code) {
        logger.info("Deleting course by code: {}", code);
        Optional<Course> courseToDelete = courseRepositoryPort.findByCode(code);
        courseToDelete.ifPresent(courseRepositoryPort::delete);
    }
}
