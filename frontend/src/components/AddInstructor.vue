<template>
  <div class="glass-panel" style="padding: 1.5rem; background: rgba(255,255,255,0.4);">
    <h3 style="margin-top: 0; margin-bottom: 1.5rem;">Add New Instructor</h3>
    <form @submit.prevent="addInstructor" style="max-width: none; padding: 0; margin: 0; display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
      <div>
        <label for="new-name">Name</label>
        <input id="new-name" v-model="newInstructor.name" placeholder="Name" required />
      </div>
      <div>
        <label for="new-surname">Surname</label>
        <input id="new-surname" v-model="newInstructor.surname" placeholder="Surname" required />
      </div>
      <div>
        <label for="new-email">Email</label>
        <input id="new-email" v-model="newInstructor.email" type="email" placeholder="Email" required />
      </div>
      <div>
        <label for="new-dept">Department</label>
        <input id="new-dept" v-model="newInstructor.department" placeholder="Department" required />
      </div>
      <div>
        <label for="new-hiringDate">Hiring Date</label>
        <input id="new-hiringDate" v-model="newInstructor.hiringDate" type="date" required />
      </div>
      <div style="grid-column: 1 / -1; display: flex; justify-content: flex-end; margin-top: 0.5rem;">
        <button type="submit" class="btn-primary" style="width: auto;">Add Instructor</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

// Defines a reactive object to hold the data for the new instructor.
const newInstructor = ref({
  name: '',
  surname: '',
  email: '',
  department: '',
  hiringDate: ''
})

// Defines a custom event emitter to notify parent components.
const emit = defineEmits(['instructor-added'])

/**
 * Asynchronously adds a new instructor.
 */
const addInstructor = async () => {
  try {
    const res = await api.createInstructor(newInstructor.value)
    if (res.status === 201) {
      newInstructor.value = {
        name: '',
        surname: '',
        email: '',
        department: '',
        hiringDate: ''
      }
      alert('Instructor added successfully!')
      emit('instructor-added')
    } else {
      alert('Failed to add instructor. Status: ' + res.status)
    }
  } catch (error) {
    console.error('Error adding instructor:', error)
    alert('Error adding instructor. Please check inputs.')
  }
}
</script>
