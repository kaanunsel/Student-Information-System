package org.pangea.sis.controller;

import jakarta.validation.Valid;
import org.pangea.sis.dto.CourseDTO;
import org.pangea.sis.dto.CourseMapper;
import org.pangea.sis.entity.Course;
import org.pangea.sis.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getCourses(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ){
        if(id != null){
            return courseService.getCourseById(id).stream().map(CourseMapper::toDto).toList();
        }
        else if (name != null){
            return courseService.getCoursesByName(name).stream().map(CourseMapper::toDto).toList();
        }
        else if (code != null){
            return courseService.getCoursesByCode(code).stream().map(CourseMapper::toDto).toList();
        }
        else {
            return courseService.getAllCourses().stream().map(CourseMapper::toDto).toList();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody @Valid CourseDTO dto){
        Course addedCourse = courseService.addCourse(CourseMapper.toEntity(dto));
        return new ResponseEntity<>(CourseMapper.toDto(addedCourse), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseDTO dto){
        return courseService.updateCourse(id, CourseMapper.toEntity(dto))
                .map(course -> new ResponseEntity<>(CourseMapper.toDto(course), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return new ResponseEntity<>("Course is deleted", HttpStatus.OK);
    }

}
