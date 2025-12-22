<template>
  <form @submit.prevent="addInstructor" class="flex flex-col gap-4">
    <BaseInput id="new-name" label="Name" v-model="newInstructor.name" required />
    <BaseInput id="new-surname" label="Surname" v-model="newInstructor.surname" required />
    <BaseInput id="new-email" label="Email" v-model="newInstructor.email" type="email" required />
    <BaseInput id="new-dept" label="Department" v-model="newInstructor.department" required />
    <BaseInput id="new-hiringDate" label="Hiring Date" v-model="newInstructor.hiringDate" type="date" required />

    <div class="flex justify-end gap-2 mt-4">
      <BaseButton type="button" variant="ghost" @click="$emit('cancel')">Cancel</BaseButton>
      <BaseButton type="submit" variant="primary">Add Instructor</BaseButton>
    </div>
  </form>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'
import BaseInput from './ui/BaseInput.vue'
import BaseButton from './ui/BaseButton.vue'

const newInstructor = ref({
  name: '',
  surname: '',
  email: '',
  department: '',
  hiringDate: ''
})

const emit = defineEmits(['instructor-added', 'cancel'])

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
