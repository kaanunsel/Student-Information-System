# SIS – Student Information System

This is a basic Student Information System project developed using Spring Boot, Vue.js, and PostgreSQL. It enables managing students, courses, enrollments, and grades in a structured and scalable way.

## Project Description

SIS allows users to:
- Track which courses a student is enrolled in
- Record and update the grades they receive
- List students enrolled in a specific course
- Add, update, and delete students and courses
- Assign or change grades for student-course pairs
- View performance analytics for each course

Note: This application does not include authentication. All operations are performed via entity IDs.

## Core Features

### Student Management
- Add, update, and delete students
- Assign an advisor (instructor) to each student
- Retrieve all students or filter by ID, name, or surname

### Course Management
- Add, update, and delete courses
- Assign an instructor to each course
- Retrieve all courses or filter by ID, name, or code

### Enrollment Management
- Enroll a student into a course
- Remove a student from a course
- Enter or update grades for a course
- View all enrollments or filter by student ID

### Analytics & Reporting
- Get average, minimum, and maximum grades for each course
- See total number of students per course
- Generate course performance summaries

## Tech Stack

- Spring Boot – Backend (Java)
- REST API – Communication Layer
- PostgreSQL – Relational Database
- JUnit – Unit Testing Framework
- Vue.js – Frontend (SPA)

## Example Endpoints

- `GET /student?id=1` – View student’s course and grade info
- `GET /course?id=2` – View course details
- `GET /enrollment/student?studentId=1` – View a student's enrollments
- `POST /student` – Add a new student
- `DELETE /course/2` – Delete a course
- `PATCH /enrollment/5/grade?grade=90` – Update a student’s grade

## Setup Instructions

1. Make sure PostgreSQL is installed and running.
2. Clone this repository.
3. Update database credentials in `application.properties`.
4. Run the backend with:

## Developer

This project was developed by **Kaan Unsel** as part of a summer internship preparation assignment.