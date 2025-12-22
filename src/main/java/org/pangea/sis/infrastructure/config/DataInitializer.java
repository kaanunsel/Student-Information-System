package org.pangea.sis.infrastructure.config;

import org.pangea.sis.infrastructure.adapter.out.persistence.entity.CourseJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.EnrollmentJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.StudentJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.CourseJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.EnrollmentJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.InstructorJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.StudentJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Initializes sample data for the application on startup.
 * Only runs if the database is empty to avoid duplicate data.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            InstructorJpaRepository instructorRepository,
            CourseJpaRepository courseRepository,
            StudentJpaRepository studentRepository,
            EnrollmentJpaRepository enrollmentRepository) {
        
        return args -> {
            // Only initialize if database is empty
            if (instructorRepository.count() > 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                return;
            }

            System.out.println("Initializing sample data...");

            // Create Instructors
            InstructorJpaEntity drSmith = new InstructorJpaEntity();
            drSmith.setName("John");
            drSmith.setSurname("Smith");
            drSmith.setEmail("john.smith@university.edu");
            drSmith.setPassword("password123");
            drSmith.setCreatedAt(LocalDateTime.now());
            drSmith = instructorRepository.save(drSmith);

            InstructorJpaEntity drJohnson = new InstructorJpaEntity();
            drJohnson.setName("Emily");
            drJohnson.setSurname("Johnson");
            drJohnson.setEmail("emily.johnson@university.edu");
            drJohnson.setPassword("password123");
            drJohnson.setCreatedAt(LocalDateTime.now());
            drJohnson = instructorRepository.save(drJohnson);

            InstructorJpaEntity drWilliams = new InstructorJpaEntity();
            drWilliams.setName("Michael");
            drWilliams.setSurname("Williams");
            drWilliams.setEmail("michael.williams@university.edu");
            drWilliams.setPassword("password123");
            drWilliams.setCreatedAt(LocalDateTime.now());
            drWilliams = instructorRepository.save(drWilliams);

            // Create Courses
            CourseJpaEntity calculus = new CourseJpaEntity();
            calculus.setName("Calculus I");
            calculus.setCode("MATH101");
            calculus.setCredit(4);
            calculus.setInstructor(drSmith);
            calculus.setCreatedAt(LocalDateTime.now());
            calculus = courseRepository.save(calculus);

            CourseJpaEntity physics = new CourseJpaEntity();
            physics.setName("Physics I");
            physics.setCode("PHYS101");
            physics.setCredit(4);
            physics.setInstructor(drJohnson);
            physics.setCreatedAt(LocalDateTime.now());
            physics = courseRepository.save(physics);

            CourseJpaEntity computerScience = new CourseJpaEntity();
            computerScience.setName("Introduction to Computer Science");
            computerScience.setCode("CS101");
            computerScience.setCredit(3);
            computerScience.setInstructor(drWilliams);
            computerScience.setCreatedAt(LocalDateTime.now());
            computerScience = courseRepository.save(computerScience);

            CourseJpaEntity dataStructures = new CourseJpaEntity();
            dataStructures.setName("Data Structures");
            dataStructures.setCode("CS201");
            dataStructures.setCredit(4);
            dataStructures.setInstructor(drWilliams);
            dataStructures.setCreatedAt(LocalDateTime.now());
            dataStructures = courseRepository.save(dataStructures);

            // Create Students
            StudentJpaEntity alice = new StudentJpaEntity();
            alice.setName("Alice");
            alice.setSurname("Brown");
            alice.setEmail("alice.brown@student.edu");
            alice.setBirthDate(LocalDate.of(2003, 5, 15));
            alice.setAdvisor(drSmith);
            alice.setCreatedAt(LocalDateTime.now());
            alice = studentRepository.save(alice);

            StudentJpaEntity bob = new StudentJpaEntity();
            bob.setName("Bob");
            bob.setSurname("Davis");
            bob.setEmail("bob.davis@student.edu");
            bob.setBirthDate(LocalDate.of(2002, 8, 22));
            bob.setAdvisor(drJohnson);
            bob.setCreatedAt(LocalDateTime.now());
            bob = studentRepository.save(bob);

            StudentJpaEntity charlie = new StudentJpaEntity();
            charlie.setName("Charlie");
            charlie.setSurname("Wilson");
            charlie.setEmail("charlie.wilson@student.edu");
            charlie.setBirthDate(LocalDate.of(2003, 2, 10));
            charlie.setAdvisor(drWilliams);
            charlie.setCreatedAt(LocalDateTime.now());
            charlie = studentRepository.save(charlie);

            StudentJpaEntity diana = new StudentJpaEntity();
            diana.setName("Diana");
            diana.setSurname("Martinez");
            diana.setEmail("diana.martinez@student.edu");
            diana.setBirthDate(LocalDate.of(2002, 11, 30));
            diana.setAdvisor(drSmith);
            diana.setCreatedAt(LocalDateTime.now());
            diana = studentRepository.save(diana);

            StudentJpaEntity evan = new StudentJpaEntity();
            evan.setName("Evan");
            evan.setSurname("Garcia");
            evan.setEmail("evan.garcia@student.edu");
            evan.setBirthDate(LocalDate.of(2003, 7, 18));
            evan.setAdvisor(drJohnson);
            evan.setCreatedAt(LocalDateTime.now());
            evan = studentRepository.save(evan);

            // Create Enrollments with Grades
            // Calculus enrollments
            createEnrollment(enrollmentRepository, alice, calculus, 85);
            createEnrollment(enrollmentRepository, bob, calculus, 92);
            createEnrollment(enrollmentRepository, charlie, calculus, 78);
            createEnrollment(enrollmentRepository, diana, calculus, 88);

            // Physics enrollments
            createEnrollment(enrollmentRepository, alice, physics, 90);
            createEnrollment(enrollmentRepository, bob, physics, 87);
            createEnrollment(enrollmentRepository, evan, physics, 95);

            // Computer Science enrollments
            createEnrollment(enrollmentRepository, charlie, computerScience, 94);
            createEnrollment(enrollmentRepository, diana, computerScience, 89);
            createEnrollment(enrollmentRepository, evan, computerScience, 91);
            createEnrollment(enrollmentRepository, alice, computerScience, 88);

            // Data Structures enrollments
            createEnrollment(enrollmentRepository, charlie, dataStructures, 82);
            createEnrollment(enrollmentRepository, diana, dataStructures, 86);
            createEnrollment(enrollmentRepository, bob, dataStructures, 79);

            System.out.println("Sample data initialized successfully!");
            System.out.println("- Instructors: " + instructorRepository.count());
            System.out.println("- Courses: " + courseRepository.count());
            System.out.println("- Students: " + studentRepository.count());
            System.out.println("- Enrollments: " + enrollmentRepository.count());
        };
    }

    private void createEnrollment(EnrollmentJpaRepository repository, StudentJpaEntity student, CourseJpaEntity course, Integer grade) {
        EnrollmentJpaEntity enrollment = new EnrollmentJpaEntity();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(grade);
        enrollment.setEnrolledAt(LocalDateTime.now().minusMonths((long) (Math.random() * 6)));
        repository.save(enrollment);
    }
}
