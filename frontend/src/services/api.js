import axios from 'axios';

const apiClient = axios.create({
    baseURL: '/api', // Proxy will handle this or full URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Since we are likely running separate dev servers, we might need full URL if not proxied
// But standard Vite setup usually proxies or we use CORS. 
// For now, let's assume relative path / to be proxied or updated later.
// Actually, let's use the default localhost:8080 if not defined.
apiClient.defaults.baseURL = 'http://localhost:8080';

export default {
    // Students
    getStudents(params) {
        return apiClient.get('/student', { params });
    },
    getStudent(id) {
        return apiClient.get(`/student?id=${id}`);
    },
    createStudent(student) {
        return apiClient.post('/student', student);
    },
    updateStudent(id, student) {
        return apiClient.put(`/student/${id}`, student);
    },
    deleteStudent(id) {
        return apiClient.delete(`/student/${id}`);
    },

    // Courses
    getCourses(params) {
        return apiClient.get('/course', { params });
    },
    createCourse(course) {
        return apiClient.post('/course', course);
    },
    updateCourse(code, course) {
        return apiClient.put(`/course/${code}`, course);
    },
    deleteCourse(code) {
        return apiClient.delete(`/course/${code}`);
    },

    // Instructors
    getInstructors() {
        return apiClient.get('/instructor');
    },
    createInstructor(instructor) {
        return apiClient.post('/instructor', instructor);
    },
    updateInstructor(id, instructor) {
        return apiClient.put(`/instructor/${id}`, instructor);
    },
    deleteInstructor(id) {
        return apiClient.delete(`/instructor/${id}`);
    },

    // Enrollments
    getEnrollments(params) {
        return apiClient.get('/enrollment', { params });
    },
    createEnrollment(enrollment) {
        return apiClient.post('/enrollment', enrollment);
    },
    updateEnrollmentGrade(id, grade) {
        return apiClient.patch(`/enrollment/${id}/grade`, null, { params: { grade } });
    },
    deleteEnrollment(id) {
        return apiClient.delete(`/enrollment/${id}`);
    },

    // Analytics
    getCoursePerformance() {
        return apiClient.get('/analytics/performance');
    }
};
