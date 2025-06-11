<template>
  <div class="container">
    <div class="list">
      <h2>Students</h2>
      <ul>
        <li
          v-for="student in students"
          :key="student.studentId"
          @click="selectStudent(student)"
        >
          {{ student.studentId }} - {{ student.name }} {{ student.surname }} -
          {{ student.email }}
        </li>
      </ul>
    </div>
    <div class="detail" v-if="selectedStudent">
      <h3>
        Courses for {{ selectedStudent.name }} {{ selectedStudent.surname }}
      </h3>
      <table>
        <thead>
          <tr>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enr in enrollments" :key="enr.id">
            <td>{{ enr.courseId }}</td>
            <td>{{ enr.courseName }}</td>
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

const students = ref([])
const selectedStudent = ref(null)
const enrollments = ref([])

const selectStudent = async (student) => {
  selectedStudent.value = student
  enrollments.value = []
  try {
    const res = await api.get('/enrollment/student', {
      params: { studentId: student.studentId }
    })
    enrollments.value = res.data
  } catch (err) {
    console.error('Failed to fetch enrollments', err)
  }
}

  onMounted(async () => {
    try {
      const response = await api.get('/student')
      students.value = response.data
    } catch (err) {
      console.error('Failed to fetch students', err)
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
