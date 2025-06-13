import { createRouter, createWebHistory} from "vue-router"
import StudentList from "./components/StudentList.vue"
import CourseList from "./components/CourseList.vue"

const routes = [
    { path: "/", redirect: "students"},
    { path: "/students", component: StudentList},
    { path: "/courses", component: CourseList}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router