package org.pangea.sis.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void findAllByCredit() {
        // given
        Integer credit1 = 5;
        Course course1 = new Course();
        course1.setCode("abc");
        course1.setCredit(credit1);
        underTest.save(course1);

        Integer credit2 = 3;
        Course course2 = new Course();
        course2.setCode("drf");
        course2.setCredit(credit2);
        underTest.save(course2);

        Integer credit3 = 5;
        Course course3 = new Course();
        course3.setCode("yty");
        course3.setCredit(credit3);
        underTest.save(course3);

        // when
        List<Course> expected = underTest.findAllByCredit(credit1);

        // then
        assertEquals(expected.size(),2);
        assertEquals(expected.getFirst().getCredit(),credit1);

    }

    @Test
    void findAllByNameContainingIgnoreCase() {
        // given
        Course course1 = new Course();
        course1.setName("Algorithms");
        underTest.save(course1);

        Course course2 = new Course();
        course2.setName("algorithms and data structures");
        underTest.save(course2);

        Course course3 = new Course();
        course3.setName("Graph Theory");
        underTest.save(course3);

        // when
        List<Course> result = underTest.findAllByNameContainingIgnoreCase("algo");

        // then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getName().toLowerCase().contains("algo")));
    }

    @Test
    void findAllByCodeContainingIgnoreCase() {
        // Given
        Course c1 = new Course();
        c1.setCode("CMPE101");
        underTest.save(c1);

        Course c2 = new Course();
        c2.setCode("cmpe102");
        underTest.save(c2);

        Course c3 = new Course();
        c3.setCode("MATH101");
        underTest.save(c3);

        // When
        List<Course> result = underTest.findAllByCodeContainingIgnoreCase("cmpe");

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getCode().toLowerCase().contains("cmpe")));
    }

    @Test
    void findByCode() {
        // Given
        Course course = new Course();
        course.setCode("PHYS123");
        course.setName("Physics");
        underTest.save(course);

        // When
        Optional<Course> result = underTest.findByCode("PHYS123");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Physics", result.get().getName());
    }

    @Test
    void findAllByInstructorId() {
        // Given
        Instructor instructor1 = new Instructor();
        instructor1.setName("Alice");
        instructor1.setEmail("alice@mail.com");
        instructor1.setPassword("123");
        entityManager.persist(instructor1);

        Instructor instructor2 = new Instructor();
        instructor2.setName("Bob");
        instructor2.setEmail("bob@mail.com");
        instructor2.setPassword("456");
        entityManager.persist(instructor2);

        Course c1 = new Course();
        c1.setCode("CS1");
        c1.setInstructor(instructor1);
        underTest.save(c1);

        Course c2 = new Course();
        c2.setCode("CS2");
        c2.setInstructor(instructor1);
        underTest.save(c2);

        Course c3 = new Course();
        c3.setCode("CS3");
        c3.setInstructor(instructor2);
        underTest.save(c3);

        // When
        List<Course> result = underTest.findAllByInstructorId(instructor1.getId());

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getInstructor().getId().equals(instructor1.getId())));
    }
}