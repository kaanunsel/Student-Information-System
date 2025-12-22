<template>
  <div class="glass-panel" style="padding: 1.5rem; background: rgba(255,255,255,0.4);">
    <h3 style="margin-top: 0; margin-bottom: 1.5rem;">Add New Student</h3>
    <form @submit.prevent="addStudent" style="max-width: none; padding: 0; margin: 0; display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
      <div>
        <label for="new-name">Name</label>
        <input id="new-name" v-model="newStudent.name" placeholder="Name" required />
      </div>
      <div>
        <label for="new-surname">Surname</label>
        <input id="new-surname" v-model="newStudent.surname" placeholder="Surname" required />
      </div>
      <div>
        <label for="new-email">Email</label>
        <input id="new-email" v-model="newStudent.email" type="email" placeholder="Email" required />
      </div>
      <div>
        <label for="new-birthDate">Birth Date</label>
        <input id="new-birthDate" v-model="newStudent.birthDate" type="date" required />
      </div>
      <div>
        <label for="new-advisorId">Advisor ID</label>
        <input id="new-advisorId" v-model="newStudent.advisorId" type="number" placeholder="Advisor ID" required />
      </div>
      <div style="grid-column: 1 / -1; display: flex; justify-content: flex-end; margin-top: 0.5rem;">
        <button type="submit" class="btn-primary" style="width: auto;">Add Student</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

// Defines a reactive object to hold the data for the new student.
const newStudent = ref({
  name: '',
  surname: '',
  email: '',
  birthDate: '',
  advisorId: null
})

// Defines a custom event emitter to notify parent components.
const emit = defineEmits(['student-added'])

/**
 * Asynchronously adds a new student by sending a POST request to the backend.
 * Resets the form on success and emits an event to refresh the student list.
 */
const addStudent = async () => {
  try {
    const res = await api.createStudent(newStudent.value)
    // Axios returns response object, check status or data
    if (res.status === 201) {
      newStudent.value = {
        name: '',
        surname: '',
        email: '',
        birthDate: '',
        advisorId: null
      }
      alert('Student added successfully!')
      emit('student-added')
    } else {
      alert('Failed to add student. Status: ' + res.status)
    }
  } catch (error) {
    console.error('Error adding student:', error)
    alert('Error adding student. Please check inputs.')
  }
}
</script> 