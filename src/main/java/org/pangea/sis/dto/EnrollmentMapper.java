package org.pangea.sis.dto;

import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.entity.Instructor;
import org.pangea.sis.entity.Student;

/**
 * Mapper class to convert between Enrollment and EnrollmentDTO objects.
 * Helps isolate data transformation logic between entity and DTO layers.
 */
public class EnrollmentMapper {

    /**
     * Converts an Enrollment entity to an EnrollmentDTO.
     *
     * @param enrollment the Enrollment entity to convert
     * @return EnrollmentDTO containing flattened data for API usage
     */
    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) return null;

        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();
        Instructor instructor = course != null ? course.getInstructor() : null;

        return new EnrollmentDTO(
                enrollment.getId(),
                student != null ? student.getId() : null,
                student != null ? student.getName() : null,
                student != null ? student.getSurname() : null,
                course != null ? course.getId() : null,
                course != null ? course.getName() : null,
                instructor != null ? instructor.getId() : null,
                instructor != null ? instructor.getName() : null,
                instructor != null ? instructor.getSurname() : null,
                enrollment.getGrade(),
                enrollment.getEnrolledAt()
        );
    }

    /**
     * Converts an EnrollmentDTO to an Enrollment entity.
     *
     * @param dto     the DTO containing enrollment data
     * @param student the student entity to assign
     * @param course  the course entity to assign
     * @return Enrollment entity constructed from DTO and related entities
     */
    public static Enrollment toEntity(EnrollmentDTO dto, Student student, Course course) {
        if (dto == null) return null;

        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(dto.getGrade());
        enrollment.setEnrolledAt(dto.getEnrolledAt());

        return enrollment;
    }
}