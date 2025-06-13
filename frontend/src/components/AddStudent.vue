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
// Import Vue's ref function to create a reactive variable for the new student.
import { ref } from 'vue'

// Create a reactive object 'newStudent' to hold the form data.
// This object will automatically update the form fields when changed.
const newStudent = ref({
  name: '',
  surname: '',
  email: '',
  birthDate: '',
  advisorId: null
})

// Function to handle form submission.
// @submit.prevent prevents the default form submission and calls addStudent instead.
const addStudent = async () => {
  try {
    // Send a POST request to the backend API with the new student data.
    const res = await fetch('http://localhost:8080/student', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newStudent.value)
    })
    if (res.ok) {
      // If successful, reset the form.
      newStudent.value = {
        name: '',
        surname: '',
        email: '',
        birthDate: '',
        advisorId: null
      }
      alert('Student added successfully!')
      // Emit an event to notify the parent component to refresh the student list.
      emit('student-added')
    } else {
      alert('Failed to add student.')
    }
  } catch (error) {
    console.error('Error adding student:', error)
    alert('Error adding student.')
  }
}

// Define the emit function to send events to the parent component.
const emit = defineEmits(['student-added'])
</script> 