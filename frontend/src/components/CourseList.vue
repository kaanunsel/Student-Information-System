<template>
  <div class="container">
    <div class="list">
      <h2>Courses</h2>
      <ul>
        <li
          v-for="course in courses"
          :key="course.courseId"
          @click="selectCourse(course)"
        >
          {{ course.courseId }} - {{ course.name }} ({{ course.code }})
        </li>
      </ul>
    </div>
    <div class="detail" v-if="selectedCourse">
      <h3>Students in {{ selectedCourse.name }}</h3>
      <table>
        <thead>
          <tr>
            <th>Student ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enr in enrollments" :key="enr.id">
            <td>{{ enr.studentId }}</td>
            <td>{{ enr.studentName }}</td>
            <td>{{ enr.studentSurname }}</td>
            <td>{{ enr.grade }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

const courses = ref([])
const selectedCourse = ref(null)
const enrollments = ref([])

const selectCourse = async (course) => {
  selectedCourse.value = course
  enrollments.value = []
  try {
    const res = await api.get('/enrollment/course', {
      params: { courseId: course.courseId }
    })
    enrollments.value = res.data
  } catch (err) {
    console.error('Failed to fetch course enrollments', err)
  }
}

onMounted(async () => {
  try {
    const response = await api.get('/course')
    courses.value = response.data
  } catch (err) {
    console.error('Failed to fetch courses', err)
  }
})
</script>

<style scoped>
.container {
  display: flex;
  gap: 20px;
}

.list {
  width: 40%;
}

.detail {
  flex: 1;
}

li {
  cursor: pointer;
  margin-bottom: 4px;
}
</style>
