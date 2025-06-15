<template>
  <div>
    <h2>Add New Student</h2>
    <form @submit.prevent="addStudent">
      <div>
        <label for="name">Name:</label>
        <input id="name" v-model="newStudent.name" required />
      </div>
      <div>
        <label for="surname">Surname:</label>
        <input id="surname" v-model="newStudent.surname" required />
      </div>
      <div>
        <label for="email">Email:</label>
        <input id="email" v-model="newStudent.email" type="email" required />
      </div>
      <div>
        <label for="birthDate">Birth Date:</label>
        <input id="birthDate" v-model="newStudent.birthDate" type="date" required />
      </div>
      <div>
        <label for="advisorId">Advisor ID:</label>
        <input id="advisorId" v-model="newStudent.advisorId" type="number" required />
      </div>
      <button type="submit">Add Student</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'

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
    const res = await fetch('http://localhost:8080/student', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newStudent.value)
    })
    if (res.ok) {
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
      alert('Failed to add student.')
    }
  } catch (error) {
    console.error('Error adding student:', error)
    alert('Error adding student.')
  }
}
</script> 