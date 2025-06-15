import { createRouter, createWebHistory} from "vue-router"
import StudentList from "./components/StudentList.vue"
import CourseList from "./components/CourseList.vue"
import InstructorList from "./components/InstructorList.vue"
import EnrollmentList from "./components/EnrollmentList.vue"
import AnalyticsList from "./components/AnalyticsList.vue"

// Defines the application's routes. Each route maps a path to a component.
const routes = [
    { path: "/", redirect: "students"},
    { path: "/students", component: StudentList},
    { path: "/courses", component: CourseList},
    { path: "/instructors", component: InstructorList},
    { path: "/enrollments", component: EnrollmentList},
    { path: "/analytics", component: AnalyticsList}
]

// Creates the router instance with web history mode and the defined routes.
const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router