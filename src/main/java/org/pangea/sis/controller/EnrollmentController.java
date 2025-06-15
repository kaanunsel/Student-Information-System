package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.dto.EnrollmentDTO;
import org.pangea.sis.dto.EnrollmentMapper;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.entity.Student;
import org.pangea.sis.service.CourseService;
import org.pangea.sis.service.EnrollmentService;
import org.pangea.sis.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing student-course enrollment operations.
 */
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    /**
     * Constructor for injecting dependencies.
     *
     * @param enrollmentService Service for enrollment-related operations.
     * @param studentService    Service for student-related operations.
     * @param courseService     Service for course-related operations.
     */
    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService, CourseService courseService){
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Retrieves all enrollments in the system, with optional filtering by student or course.
     *
     * @param studentId Optional ID of the student to filter by.
     * @param courseId  Optional ID of the course to filter by.
     * @return A list of matching {@link EnrollmentDTO} objects.
     */
    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId
    ){
        if(studentId != null && courseId != null){
            return enrollmentService.getAllEnrollmentsByStudentAndCourseId(studentId,courseId).stream().map(EnrollmentMapper::toDTO).toList();
        }else if(studentId != null){
            return enrollmentService.getByStudentId(studentId).stream().map(EnrollmentMapper::toDTO).toList();
        }else if(courseId != null){
            return enrollmentService.getByCourseId(courseId).stream().map(EnrollmentMapper::toDTO).toList();
        }else{
            return enrollmentService.getAllEnrollments().stream().map(EnrollmentMapper::toDTO).toList();
        }
    }

    /**
     * Creates a new enrollment using student and course IDs.
     *
     * @param dto enrollment data
     * @return created enrollment as DTO
     */
    @PostMapping
    public ResponseEntity<EnrollmentDTO> addEnrollment(@RequestBody @Valid EnrollmentDTO dto){
        Student enrolledStudent = studentService.getStudentById(dto.getStudentId()).getFirst();
        Course enrolledCourse = courseService.getCourseById(dto.getCourseId()).getFirst();
        Enrollment createdEnrollment = enrollmentService.createEnrollment(
                EnrollmentMapper.toEntity(dto, enrolledStudent, enrolledCourse)
        );
        return new ResponseEntity<>(EnrollmentMapper.toDTO(createdEnrollment), HttpStatus.CREATED);
    }

    /**
     * Updates the grade of a specific enrollment.
     *
     * @param id ID of the enrollment
     * @param grade new grade to assign
     * @return updated enrollment as DTO
     */
    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(
            @PathVariable Long id,
            @RequestParam Integer grade
    ){
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade));
    }

    /**
     * Deletes an enrollment by its ID.
     *
     * @param id enrollment ID
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable Long id){
        enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>("Enrollment deleted", HttpStatus.OK);
    }

}