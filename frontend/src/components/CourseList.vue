<template>
  <div>
    <h2>Courses</h2>
    <ul>
      <li v-for="course in courses" :key="course.id">
        {{ course.name }} ({{ course.code }})
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

const courses = ref([])

onMounted(async () => {
  try {
    const response = await api.get('/course')
    courses.value = response.data
  } catch (err) {
    console.error('Failed to fetch courses', err)
  }
})
</script>
