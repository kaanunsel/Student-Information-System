<template>
  <div>
    <h2>Students</h2>
    <ul>
      <li v-for="student in students" :key="student.id">
        {{ student.name }} {{ student.surname }}
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

const students = ref([])

onMounted(async () => {
  try {
    const response = await api.get('/student')
    students.value = response.data
  } catch (err) {
    console.error('Failed to fetch students', err)
  }
})
</script>
