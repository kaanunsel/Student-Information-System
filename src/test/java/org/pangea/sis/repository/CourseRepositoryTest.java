package org.pangea.sis.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;

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
    @Disabled
    void findAllByNameContainingIgnoreCase() {
    }

    @Test
    @Disabled
    void findAllByCodeContainingIgnoreCase() {
    }

    @Test
    @Disabled
    void findByCode() {
    }

    @Test
    @Disabled
    void findAllByInstructorId() {
    }
}