<template>
  <form @submit.prevent="addStudent" class="flex flex-col gap-4">
    <BaseInput id="new-name" label="Name" v-model="newStudent.name" required />
    <BaseInput id="new-surname" label="Surname" v-model="newStudent.surname" required />
    <BaseInput id="new-email" label="Email" v-model="newStudent.email" type="email" required />
    <BaseInput id="new-birthDate" label="Birth Date" v-model="newStudent.birthDate" type="date" required />
    <BaseInput id="new-advisorId" label="Advisor ID" v-model="newStudent.advisorId" type="number" required />

    <div class="flex justify-end gap-2 mt-4">
      <BaseButton type="button" variant="ghost" @click="$emit('cancel')">Cancel</BaseButton>
      <BaseButton type="submit" variant="primary">Add Student</BaseButton>
    </div>
  </form>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'
import BaseInput from './ui/BaseInput.vue'
import BaseButton from './ui/BaseButton.vue'

const newStudent = ref({
  name: '',
  surname: '',
  email: '',
  birthDate: '',
  advisorId: null
})

const emit = defineEmits(['student-added', 'cancel'])

const addStudent = async () => {
  try {
    const res = await api.createStudent(newStudent.value)
    if (res.status === 201) {
      newStudent.value = {
        name: '',
        surname: '',
        email: '',
        birthDate: '',
        advisorId: null
      }
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