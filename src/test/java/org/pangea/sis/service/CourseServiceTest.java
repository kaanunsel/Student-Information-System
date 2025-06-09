package org.pangea.sis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Course;
import org.pangea.sis.repository.CourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private CourseRepository courseRepository;
    private CourseService courseService;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);
    }

    @Test
    void testGetAllCourses() {
        Course c1 = new Course();
        Course c2 = new Course();
        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Course> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById_found() {
        Course course = new Course();
        course.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        List<Course> result = courseService.getCourseById(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(courseRepository).findById(1L);
    }

    @Test
    void testGetCourseById_notFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        List<Course> result = courseService.getCourseById(99L);

        assertTrue(result.isEmpty());
        verify(courseRepository).findById(99L);
    }

    @Test
    void testGetCoursesByName() {
        String name = "Math";
        Course course = new Course();
        course.setName("Mathematics");
        when(courseRepository.findAllByNameContainingIgnoreCase(name)).thenReturn(List.of(course));

        List<Course> result = courseService.getCoursesByName(name);

        assertEquals(1, result.size());
        assertEquals("Mathematics", result.get(0).getName());
    }

    @Test
    void testGetCoursesByCode() {
        String code = "CS";
        Course course = new Course();
        course.setCode("CS101");
        when(courseRepository.findAllByCodeContainingIgnoreCase(code)).thenReturn(List.of(course));

        List<Course> result = courseService.getCoursesByCode(code);

        assertEquals(1, result.size());
        assertEquals("CS101", result.get(0).getCode());
    }

    @Test
    void testGetCoursesByInstructorId() {
        Long instructorId = 10L;
        Course course = new Course();
        when(courseRepository.findAllByInstructorId(instructorId)).thenReturn(List.of(course));

        List<Course> result = courseService.getCoursesByInstructorId(instructorId);

        assertEquals(1, result.size());
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course saved = courseService.addCourse(course);

        assertNotNull(saved);
        verify(courseRepository).save(course);
    }

    @Test
    void testDeleteCourseById() {
        Long courseId = 5L;

        courseService.deleteCourseById(courseId);

        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void testUpdateCourse_found() {
        Course existing = new Course();
        existing.setCode("CS101");
        existing.setName("Old Name");
        existing.setCredit(3);

        Course updated = new Course();
        updated.setCode("CS101");
        updated.setName("New Name");
        updated.setCredit(4);

        when(courseRepository.findByCode("CS101")).thenReturn(Optional.of(existing));
        when(courseRepository.save(existing)).thenReturn(existing);

        Optional<Course> result = courseService.updateCourse("CS101", updated);

        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        assertEquals(4, result.get().getCredit());
    }

    @Test
    void testUpdateCourse_notFound() {
        Course updated = new Course();
        updated.setCode("CS999");
        updated.setName("Ghost Course");

        when(courseRepository.findByCode("CS999")).thenReturn(Optional.empty());

        Optional<Course> result = courseService.updateCourse("CS999", updated);

        assertTrue(result.isEmpty());
    }
}