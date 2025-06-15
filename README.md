# Student Information System (SIS)

This is a full-stack web application built to manage students, courses, and enrollments at a university.

This system is allowing administrators to easily track student progress, manage course offerings, and get insights into academic performance.

## Features

#### Student Management
- **Add, Update & Delete Students**: Easily manage the student roster.
- **Filter Students**: Quickly find students by ID, name, or surname.
- **Assign Advisors**: Link students to an instructor for academic advising.

#### Course Management
- **Add, Update & Delete Courses**: Keep the course catalog current.
- **Filter Courses**: Search for courses by ID, name, or code.
- **Assign Instructors**: Assign a lead instructor to each course.

#### Enrollment Management
- **Enroll Students**: Enroll a student into a course.
- **Update Grades**: Record or change a student's grade for a course.
- **Remove Enrollments**: Unenroll a student from a course.
- **View Enrollments**: See all enrollments or filter by student.

#### Analytics & Reporting
- **Course Performance**: View a summary for each course, including:
  - Average, minimum, and maximum grades.
  - Total number of enrolled students.

## Technologies Used

#### Backend
- **Spring Boot**: For building a REST API.
- **Spring Data JPA**: For object-relational mapping and database interaction.
- **PostgreSQL**: The relational database for storing all data.
- **Maven**: For project dependency management.

#### Frontend
- **Vue.js**: A progressive JavaScript framework for building the user interface.
- **Vite**: A fast and modern frontend build tool.
- **Axios**: For making HTTP requests from the frontend to the backend API.

## Getting Started

To get the application running on your local machine, follow these steps.

#### Prerequisites
- **Java 17** or higher
- **Maven 3.8** or higher
- **Node.js 18** or higher
- A running **PostgreSQL** instance

#### 1. Configure the Database
1. Make sure your PostgreSQL server is running.
2. Create a database named `universitydb`.
3. Open `src/main/resources/application.properties` and update the `spring.datasource.username` and `spring.datasource.password` fields with your PostgreSQL credentials.

#### 2. Run the Backend
Open a terminal and navigate to the project's root directory. Run the following Maven command:
```bash
mvn spring-boot:run
```
The backend server will start on `http://localhost:8080`.

#### 3. Run the Frontend
1. Open a new terminal.
2. Navigate to the `frontend` directory: `cd frontend`.
3. Install the necessary packages: `pnpm install`.
4. Start the development server: `pnpm dev`.

The frontend application will be available at `http://localhost:5173`.

## API Endpoints

The backend provides a RESTful API to interact with the data. Here are the main endpoints:

| Method | Endpoint                    | Description                                  |
|--------|-----------------------------|----------------------------------------------|
| `GET`    | `/student`                  | Get a list of students (with filters)        |
| `POST`   | `/student`                  | Add a new student                            |
| `PUT`    | `/student/{id}`             | Update an existing student                   |
| `DELETE` | `/student/{id}`             | Delete a student                             |
| `GET`    | `/course`                   | Get a list of courses (with filters)         |
| `POST`   | `/course`                   | Add a new course                             |
| `PUT`    | `/course/{code}`            | Update a course by its code                  |
| `DELETE` | `/course/{code}`            | Delete a course by its code                  |
| `GET`    | `/enrollment`               | Get a list of enrollments (with filters)     |
| `POST`   | `/enrollment`               | Create a new enrollment                      |
| `DELETE` | `/enrollment/{id}`          | Delete an enrollment                         |
| `PATCH`  | `/enrollment/{id}/grade`    | Update the grade for an enrollment           |
| `GET`    | `/analytics/performance`    | Get course performance statistics            |

Thank you.