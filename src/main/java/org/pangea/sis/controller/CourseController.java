package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.dto.CourseDTO;
import org.pangea.sis.dto.CourseMapper;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.service.CourseService;
import org.pangea.sis.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing course-related endpoints.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final InstructorService instructorService;

    /**
     * Constructor for injecting CourseService and InstructorService.
     */
    @Autowired
    public CourseController(CourseService courseService, InstructorService instructorService){
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    /**
     * Retrieves courses based on optional filters: ID, name, or code.
     * If no filter is provided, returns all courses.
     *
     * @param id   optional course ID
     * @param instructorId optional instructor ID
     * @param code optional course code
     * @return list of matching CourseDTOs
     */
    @GetMapping
    public List<CourseDTO> getCourses(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Long instructorId
    ){
        if(id != null){
            return courseService.getCourseById(id).stream().map(CourseMapper::toDto).toList();
        }
        else if (code != null){
            return courseService.getCoursesByCode(code).stream().map(CourseMapper::toDto).toList();
        }
        else if (instructorId != null){
            return courseService.getCoursesByInstructorId(instructorId).stream().map(CourseMapper::toDto).toList();
        }
        else {
            return courseService.getAllCourses().stream().map(CourseMapper::toDto).toList();
        }
    }

    /**
     * Adds a new course to the system.
     *
     * @param dto course data with instructor ID
     * @return the created course DTO or 404 if instructor not found
     */
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody @Valid CourseDTO dto){
        Optional<Instructor> addedInstructor = instructorService.getInstructorById(dto.getInstructorId());
        if(addedInstructor.isEmpty()){
            return new ResponseEntity<String>("No such instructor", HttpStatus.NOT_FOUND);
        }
        Course addedCourse = courseService.addCourse(CourseMapper.toEntity(dto, addedInstructor.get()));
        return new ResponseEntity<>(CourseMapper.toDto(addedCourse), HttpStatus.CREATED);
    }

    /**
     * Updates a course by its code.
     *
     * @param code course code
     * @param dto  updated course data
     * @return updated course DTO or 404 if not found
     */
    @PutMapping("/{code}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String code, @RequestBody @Valid CourseDTO dto){
        Instructor instructor = instructorService.getInstructorById(dto.getInstructorId()).get();
        return courseService.updateCourse(code, CourseMapper.toEntity(dto, instructor))
                .map(course -> new ResponseEntity<>(CourseMapper.toDto(course), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a course by its ID.
     *
     * @param id course ID
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return new ResponseEntity<>("Course is deleted", HttpStatus.OK);
    }

}