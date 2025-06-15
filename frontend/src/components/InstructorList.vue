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

// --- Reactive State ---

// Holds the list of instructors fetched from the backend.
const instructors = ref([])
// Holds the instructor object currently being edited, or null if not in edit mode.
const editingInstructor = ref(null)

// --- Core Logic ---

/**
 * Fetches the list of instructors from the backend.
 */
const refreshInstructors = async () => {
    const res = await fetch("http://localhost:8080/instructor")
    instructors.value = await res.json()
}

/**
 * Initiates the editing process for an instructor.
 * @param {object} instructor The instructor object to be edited.
 */
function startEdit(instructor){
    editingInstructor.value = {...instructor}
}

/**
 * Cancels the editing process and clears the editing state.
 */
function cancelEdit(){
    editingInstructor.value = null
}

/**
 * Submits the updated instructor data to the backend.
 */
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
    console.error('Error updating instructor:', e)
    alert('Error updating instructor.')
  }
}

/**
 * Deletes an instructor after confirming with the user.
 * @param {number} id The ID of the instructor to be deleted.
 */
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
    console.error('Error deleting instructor:', e)
    alert('Error deleting instructor.')
  }
}

// --- Lifecycle Hooks ---

// Fetches the initial list of instructors when the component is mounted.
onMounted(refreshInstructors)
</script>