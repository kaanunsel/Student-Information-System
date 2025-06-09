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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService, CourseService courseService){
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments(){
        return enrollmentService.getAllEnrollments().stream().map(EnrollmentMapper::toDTO).toList();
    }

    @GetMapping("/student")
    public List<EnrollmentDTO> getByStudentId(@RequestParam Long studentId){
        return enrollmentService.getByStudentId(studentId).stream().map(EnrollmentMapper::toDTO).toList();
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> addEnrollment(@RequestBody @Valid EnrollmentDTO dto){
        Student enrolledStudent = studentService.getStudentById(dto.getStudentId()).getFirst();
        Course enrolledCourse = courseService.getCourseById(dto.getCourseId()).getFirst();
        Enrollment createdEnrollment = enrollmentService.createEnrollment(
                EnrollmentMapper.toEntity(dto, enrolledStudent, enrolledCourse)
        );
        return new ResponseEntity<>(EnrollmentMapper.toDTO(createdEnrollment), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(
            @PathVariable Long id,
            @RequestParam Integer grade
    ){
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable Long id){
        enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>("Enrollment deleted", HttpStatus.OK);
    }

}
