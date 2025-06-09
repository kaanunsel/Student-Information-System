package org.pangea.sis.service;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.CourseRepository;

import static org.mockito.Mockito.mock;

public class CourseServiceTest {

    private CourseRepository courseRepository;
    private CourseService courseService;

    @BeforeEach
    void setup(){
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);
    }



}
