package org.pangea.sis.dto;

import org.pangea.sis.entity.Course;
import org.pangea.sis.entity.Enrollment;
import org.pangea.sis.entity.Student;

public class EnrollmentMapper {

    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) return null;

        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();

        return new EnrollmentDTO(
                enrollment.getId(),
                student != null ? student.getId() : null,
                student != null ? student.getName() : null,
                student != null ? student.getSurname() : null,
                course != null ? course.getId() : null,
                course != null ? course.getName() : null,
                enrollment.getGrade(),
                enrollment.getEnrolledAt()
        );
    }

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