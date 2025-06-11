<template>
  <div class="container">
    <div class="list">
      <h2>Instructors</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ins in instructors" :key="ins.id">
            <td>{{ ins.id }}</td>
            <td>{{ ins.name }}</td>
            <td>{{ ins.surname }}</td>
            <td>{{ ins.email }}</td>
            <td><button @click="removeInstructor(ins.id)">Delete</button></td>
          </tr>
        </tbody>
      </table>
      <div class="add-form">
        <h3>Add Instructor</h3>
        <input v-model="newInstructor.name" placeholder="Name" />
        <input v-model="newInstructor.surname" placeholder="Surname" />
        <input v-model="newInstructor.email" placeholder="Email" />
        <input v-model="newInstructor.password" placeholder="Password" type="password" />
        <button @click="addInstructor">Add</button>
      </div>
    </div>
  </div>
</template>

<script setup>
// Component that lists instructors and allows adding or removing them
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({ baseURL: 'http://localhost:8080' })

// all instructors returned from backend
const instructors = ref([])
// model for the "add instructor" form
const newInstructor = ref({ name: '', surname: '', email: '', password: '' })

// fetch instructor list from API
const load = async () => {
  try {
    const res = await api.get('/instructor')
    instructors.value = res.data
  } catch (err) {
    console.error('Failed to fetch instructors', err)
  }
}

// create a new instructor
const addInstructor = async () => {
  try {
    await api.post('/instructor', newInstructor.value)
    await load()
    newInstructor.value = { name: '', surname: '', email: '', password: '' }
  } catch (err) {
    console.error('Failed to add instructor', err)
  }
}

// delete instructor by id
const removeInstructor = async (id) => {
  try {
    await api.delete(`/instructor/${id}`)
    await load()
  } catch (err) {
    console.error('Failed to remove instructor', err)
  }
}

// initial load of instructors
onMounted(load)
</script>

<style scoped>
.container {
  display: flex;
  width: 90%;
  margin: auto;
}

.list {
  width: 100%;
}

.add-form {
  margin-top: 1rem;
}

button {
  cursor: pointer;
}

th,
td {
  padding: 6px 8px;
}
</style>
