**SIS – Student Information System**

This is a basic Student Information System project developed using Spring Boot, Vue.js, and PostgreSQL. It allows managing students, courses, and their grades in a simple and straightforward way.

**Project Description**

SIS helps track:
	•	Which courses a student is taking
	•	The grades they received
	•	Which students are enrolled in a course
	•	Adding and removing students and courses
	•	Assigning or updating grades

Note: There is no login system. All queries work directly with ID values.

**Tech Stack**
Spring Boot	for Backend (Java API)
REST API	for Communication (HTTP methods)
PostgreSQL for Database
JUnit	for Unit testing
Vue.js for Frontend

**Example Features**
	•	GET /students/{id}: See a student’s courses and grades
	•	GET /courses/{id}/students: See all students in a course
	•	POST /students: Add a new student
	•	DELETE /courses/{id}: Remove a course
	•	PUT /students/{id}/courses/{courseId}/grade: Update a grade

**Setup**
	1.	Make sure PostgreSQL is installed and running.
	2.	Clone this repository.
	3.	Configure your database credentials in application.properties.
	4.	Run the application with IntelliJ or use:  ./mvnw spring-boot:run

Developed by **Kaan Unsel** as a summer internship project.
