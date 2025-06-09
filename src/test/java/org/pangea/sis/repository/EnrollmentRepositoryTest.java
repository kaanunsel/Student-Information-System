package org.pangea.sis.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.pangea.sis.entity.Student;
import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.dto.CoursePerformanceDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EnrollmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private Student student1;
    private Student student2;
    private Course course1;
    private Course course2;
    private Enrollment enrollment1;
    private Enrollment enrollment2;

    @BeforeEach
    void setUp() {
        // create students
        student1 = new Student();
        student1.setName("Alice");
        student1.setSurname("Smith");
        student1.setEmail("alice@example.com");
        student1.setBirthDate(LocalDate.of(2000, 1, 1));
        student1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(student1);

        student2 = new Student();
        student2.setName("Bob");
        student2.setSurname("Johnson");
        student2.setEmail("bob@example.com");
        student2.setBirthDate(LocalDate.of(2001, 2, 2));
        student2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(student2);

        // create courses
        course1 = new Course();
        course1.setName("Math");
        course1.setCode("MATH101");
        course1.setCredit(4);
        course1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(course1);

        course2 = new Course();
        course2.setName("History");
        course2.setCode("HIST202");
        course2.setCredit(3);
        course2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(course2);

        // create enrollments
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(90);
        enrollment1.setEnrolledAt(LocalDateTime.now());
        entityManager.persist(enrollment1);

        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course2);
        enrollment2.setGrade(75);
        enrollment2.setEnrolledAt(LocalDateTime.now());
        entityManager.persist(enrollment2);

        entityManager.flush();
    }

    @Test
    void findByStudentId() {
        List<Enrollment> result = enrollmentRepository.findByStudentId(student1.getId());
        assertEquals(1, result.size());
        assertEquals(enrollment1.getId(), result.get(0).getId());
    }

    @Test
    void findByCourseId() {
        List<Enrollment> result = enrollmentRepository.findByCourseId(course2.getId());
        assertEquals(1, result.size());
        assertEquals(enrollment2.getId(), result.get(0).getId());
    }

    @Test
    void findByStudentIdAndCourseId() {
        Optional<Enrollment> opt = enrollmentRepository.findByStudentIdAndCourseId(student1.getId(), course1.getId());
        assertTrue(opt.isPresent());
        assertEquals(enrollment1.getId(), opt.get().getId());
    }

    @Test
    void findByGradeIsNotNull() {
        List<Enrollment> result = enrollmentRepository.findByGradeIsNotNull();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getGrade().equals(90)));
        assertTrue(result.stream().anyMatch(e -> e.getGrade().equals(75)));
    }

    @Test
    void getCoursePerformanceSummary() {
        List<CoursePerformanceDTO> summary = enrollmentRepository.getCoursePerformanceSummary();
        assertEquals(2, summary.size());

        CoursePerformanceDTO dto1 = summary.stream()
                .filter(s -> s.getCourseId().equals(course1.getId()))
                .findFirst()
                .orElseThrow();
        assertEquals(90.0, dto1.getAvgGrade());
        assertEquals(1L, dto1.getNumberOfStudents() );
        assertEquals(90, dto1.getMinGrade());
        assertEquals(90, dto1.getMaxGrade());

        CoursePerformanceDTO dto2 = summary.stream()
                .filter(s -> s.getCourseId().equals(course2.getId()))
                .findFirst()
                .orElseThrow();
        assertEquals(75.0, dto2.getAvgGrade());
        assertEquals(1L, dto2.getNumberOfStudents());
        assertEquals(75, dto2.getMinGrade());
        assertEquals(75, dto2.getMaxGrade());
    }
}