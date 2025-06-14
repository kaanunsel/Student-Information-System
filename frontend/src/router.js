import { createRouter, createWebHistory} from "vue-router"
import StudentList from "./components/StudentList.vue"
import CourseList from "./components/CourseList.vue"
import InstructorList from "./components/InstructorList.vue"
import EnrollmentList from "./components/EnrollmentList.vue"

const routes = [
    { path: "/", redirect: "students"},
    { path: "/students", component: StudentList},
    { path: "/courses", component: CourseList},
    { path: "/instructors", component: InstructorList},
    { path: "/enrollments", component: EnrollmentList}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router