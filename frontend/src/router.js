import { createRouter, createWebHistory } from 'vue-router'
// route components
import StudentList from './components/StudentList.vue'
import CourseList from './components/CourseList.vue'
import InstructorList from './components/InstructorList.vue'
import StatsView from './components/StatsView.vue'

// application routes
const routes = [
  { path: '/', component: StudentList },
  { path: '/courses', component: CourseList },
  { path: '/instructors', component: InstructorList },
  { path: '/stats', component: StatsView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// export router instance for use in main.js
export default router
