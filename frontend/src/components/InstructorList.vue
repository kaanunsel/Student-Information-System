<template>
    <div>
        <h2>Instructor List</h2>
        <table border="1">
        <thead>
            <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>Edit</th>
            <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="instructor in instructors" :key="instructor.id">
            <td>{{ instructor.id }}</td>
            <td>{{ instructor.name }}</td>
            <td>{{ instructor.surname }}</td>
            <td>{{ instructor.email }}</td>
            <td><button @click="startEdit(instructor)">Edit</button></td>
            <td><button @click="deleteInstructor(instructor.id)">Delete</button></td>
            </tr>
        </tbody>
        </table>
        <div v-if="editingInstructor">
        <h3>Edit Instructor</h3>
        <form @submit.prevent="submitEdit">
            <div>
                <label for="name">Name:</label>
                <input id="name" v-model="editingInstructor.name" placeholder="Name" required />
            </div>
            <div>
                <label for="surname">Surname:</label>
                <input id="surname" v-model="editingInstructor.surname" placeholder="Surname" required />
            </div>
            <div>
                <label for="email">Email:</label>
                <input id="email" v-model="editingInstructor.email" placeholder="Email" required />
            </div>
            <button type="submit">Save</button>
            <button type="button" @click="cancelEdit">Cancel</button>
        </form>
        </div>
    </div>
</template>

<script setup>

import { ref, onMounted } from "vue"

const instructors = ref([])
const editingInstructor = ref(null)

const refreshInstructors = async () => {
    const res = await fetch("http://localhost:8080/instructor")
    instructors.value = await res.json()
}

function startEdit(instructor){
    editingInstructor.value = {...instructor}
}

function cancelEdit(){
    editingInstructor.value = null
}

async function submitEdit(){
    try {
    const res = await fetch(`http://localhost:8080/instructor/${editingInstructor.value.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editingInstructor.value)
    })
    if (res.ok) {
      await refreshInstructors()
      editingInstructor.value = null
      alert('Instructor updated!')
    } else {
      alert('Failed to update instructor.')
    }
  } catch (e) {
    alert('Error updating instructor.')
  }
}

async function deleteInstructor(id) {
  if (!confirm('Are you sure you want to delete this instructor?')) return
  try {
    const res = await fetch(`http://localhost:8080/instructor/${id}`, {
      method: 'DELETE'
    })
    if (res.ok) {
      await refreshInstructors()
      alert('Instructor deleted!')
    } else {
      alert('Failed to delete instructor.')
    }
  } catch (e) {
    alert('Error deleting instructor.')
  }
}

onMounted(refreshInstructors)
</script>