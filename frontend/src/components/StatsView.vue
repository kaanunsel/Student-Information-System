<template>
  <div class="container">
    <h2>Statistics</h2>
    <div class="summary">
      <p>Total Students: {{ students.length }}</p>
      <p>Total Instructors: {{ instructors.length }}</p>
    </div>
    <h3>Course Performance</h3>
    <table>
      <thead>
        <tr>
          <th>Course ID</th>
          <th>Course Name</th>
          <th>Avg Grade</th>
          <th>Student Count</th>
          <th>Min Grade</th>
          <th>Max Grade</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="c in courseStats" :key="c.courseId">
          <td>{{ c.courseId }}</td>
          <td>{{ c.courseName }}</td>
          <td>{{ c.avgGrade.toFixed(2) }}</td>
          <td>{{ c.numberOfStudents }}</td>
          <td>{{ c.minGrade }}</td>
          <td>{{ c.maxGrade }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
// Dashboard view presenting high level statistics
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({ baseURL: 'http://localhost:8080' })

// arrays used to store fetched statistics
const students = ref([])
const instructors = ref([])
const courseStats = ref([])

// retrieve summary stats from backend
const load = async () => {
  try {
    const [sRes, iRes, cRes] = await Promise.all([
      api.get('/student'),
      api.get('/instructor'),
      api.get('/analytics/performance')
    ])
    students.value = sRes.data
    instructors.value = iRes.data
    courseStats.value = cRes.data
  } catch (err) {
    console.error('Failed to load stats', err)
  }
}

// fetch data once component is mounted
onMounted(load)
</script>

<style scoped>
.container {
  width: 80%;
  margin: auto;
  background: #fff;
  padding: 1rem;
  border-radius: 4px;
}

.summary {
  margin-bottom: 1rem;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ccc;
  padding: 6px 8px;
  text-align: left;
}

tr:hover {
  background: #f1f1f1;
}
</style>
