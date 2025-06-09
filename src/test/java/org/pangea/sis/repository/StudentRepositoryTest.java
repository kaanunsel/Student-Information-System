package org.pangea.sis.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.pangea.sis.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link StudentRepository}.
 * Uses in-memory database to verify repository methods.
 */
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    /**
     * Tests whether the repository correctly retrieves students by their name.
     * Inserts multiple students and verifies that only those with the given name are returned.
     */
    @Test
    void ShouldFindAllByName() {
        // given
        String email = "1kaanunsel@hotmail.com";
        Student student = new Student();
        student.setName("Kaan");
        student.setSurname("Unsel");
        student.setEmail(email);
        underTest.save(student);

        String email2 = "2kaanunsel@hotmail.com";
        Student student2 = new Student();
        student2.setName("Ahmet");
        student2.setSurname("Unsel");
        student2.setEmail(email2);
        underTest.save(student2);

        String email3 = "3kaanunsel@hotmail.com";
        Student student3 = new Student();
        student3.setName("Mehmet");
        student3.setSurname("Unsel");
        student3.setEmail(email3);
        underTest.save(student3);

        // when
        List<Student> expected = underTest.findAllByName("Mehmet");

        // then
        assertEquals("Mehmet", expected.getFirst().getName());
    }

    /**
     * Tests whether the repository correctly retrieves a student by their email.
     * Inserts a student and checks that findByEmail returns the expected student.
     */
    @Test
    void ShouldFindByEmail() {
        // given
        String email = "kaanunsel@hotmail.com";
        Student student = new Student();
        student.setName("Kaan");
        student.setSurname("Unsel");
        student.setEmail(email);
        underTest.save(student);

        // when
        Optional<Student> expected = underTest.findByEmail(email);

        // then
        assertTrue(expected.isPresent());
        assertEquals("Kaan", expected.get().getName());
    }
}