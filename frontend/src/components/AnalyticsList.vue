<template>
  <div class="analytics-list">
    <h2>Course Performance Analytics</h2>

    <!-- Analytics Table -->
    <div class="analytics-table">
      <table border="1">
        <thead>
          <tr>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Number of Students</th>
            <th>Average Grade</th>
            <th>Min Grade</th>
            <th>Max Grade</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stat in stats" :key="stat.courseId">
            <td>{{ stat.courseId }}</td>
            <td>{{ stat.courseName }}</td>
            <td>{{ stat.numberOfStudents }}</td>
            <td>{{ stat.avgGrade ? stat.avgGrade.toFixed(2) : 'N/A' }}</td>
            <td>{{ stat.minGrade !== null ? stat.minGrade : 'N/A' }}</td>
            <td>{{ stat.maxGrade !== null ? stat.maxGrade : 'N/A' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Defines a reactive array to store the course performance statistics.
const stats = ref([])

/**
 * Asynchronously fetches course performance statistics from the backend
 * and populates the stats array.
 */
const fetchStats = async () => {
  try {
    const response = await axios.get('http://localhost:8080/analytics/performance')
    stats.value = response.data
  } catch (error) {
    console.error('Error fetching analytics:', error)
    alert('Failed to fetch analytics data.')
  }
}

// Fetches the statistics when the component is first mounted.
onMounted(fetchStats)
</script>