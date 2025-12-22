package org.pangea.sis.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.pangea.sis.domain.model.Course;
import org.pangea.sis.domain.port.in.CourseUseCase;
import org.pangea.sis.infrastructure.adapter.in.web.dto.CourseDTO;
import org.pangea.sis.infrastructure.adapter.in.web.mapper.CourseWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing course-related endpoints.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseUseCase courseUseCase;

    public CourseController(CourseUseCase courseUseCase) {
        this.courseUseCase = courseUseCase;
    }

    /**
     * Retrieves courses based on optional filters: ID, name, or code.
     * If no filter is provided, returns all courses.
     *
     * @param id           optional course ID
     * @param instructorId optional instructor ID
     * @param name         optional course name
     * @param code         optional course code
     * @return list of matching CourseDTOs
     */
    @GetMapping
    public List<CourseDTO> getCourses(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Long instructorId,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            return courseUseCase.getCourseById(id).stream()
                    .map(CourseWebMapper::toDto)
                    .toList();
        } else if (code != null) {
            return courseUseCase.getCoursesByCode(code).stream()
                    .map(CourseWebMapper::toDto)
                    .toList();
        } else if (instructorId != null) {
            return courseUseCase.getCoursesByInstructorId(instructorId).stream()
                    .map(CourseWebMapper::toDto)
                    .toList();
        } else if (name != null) {
            return courseUseCase.getCoursesByName(name).stream()
                    .map(CourseWebMapper::toDto)
                    .toList();
        } else {
            return courseUseCase.getAllCourses().stream()
                    .map(CourseWebMapper::toDto)
                    .toList();
        }
    }

    /**
     * Adds a new course to the system.
     *
     * @param dto course data with instructor ID
     * @return the created course DTO
     */
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody @Valid CourseDTO dto) {
        Course course = CourseWebMapper.toDomain(dto);
        Course addedCourse = courseUseCase.addCourse(course);
        return new ResponseEntity<>(CourseWebMapper.toDto(addedCourse), HttpStatus.CREATED);
    }

    /**
     * Updates a course by its code.
     *
     * @param code course code
     * @param dto  updated course data
     * @return updated course DTO or 404 if not found
     */
    @PutMapping("/{code}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String code, @RequestBody @Valid CourseDTO dto) {
        Course course = CourseWebMapper.toDomain(dto);
        return courseUseCase.updateCourse(code, course)
                .map(updatedCourse -> new ResponseEntity<>(CourseWebMapper.toDto(updatedCourse), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a course by its code.
     *
     * @param code course code
     * @return confirmation message
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteCourse(@PathVariable String code) {
        courseUseCase.deleteCourseByCode(code);
        return new ResponseEntity<>("Course is deleted", HttpStatus.OK);
    }
}
