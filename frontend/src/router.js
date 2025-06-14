import { createRouter, createWebHistory} from "vue-router"
import StudentList from "./components/StudentList.vue"
import CourseList from "./components/CourseList.vue"
import InstructorList from "./components/InstructorList.vue"

const routes = [
    { path: "/", redirect: "students"},
    { path: "/students", component: StudentList},
    { path: "/courses", component: CourseList},
    { path: "/instructors", component: InstructorList}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router