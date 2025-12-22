<template>
    <div class="glass-panel" style="padding: 2rem;">
        <div class="page-header">
            <h2>Instructor List</h2>
        </div>

        <div class="table-container">
            <table class="instructor-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Department</th>
                        <th>Hiring Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="instructor in instructors" :key="instructor.id">
                        <td>{{ instructor.id }}</td>
                        <td>{{ instructor.name }}</td>
                        <td>{{ instructor.surname }}</td>
                        <td>{{ instructor.email }}</td>
                        <td>{{ instructor.department }}</td>
                        <td>{{ instructor.hiringDate }}</td>
                        <td>
                            <div style="display: flex; gap: 0.5rem;">
                                <button class="btn-secondary" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="startEdit(instructor)">Edit</button>
                                <button class="btn-danger" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="deleteInstructor(instructor.id)">Delete</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div style="margin-top: 2rem;">
            <AddInstructor @instructor-added="refreshInstructors"></AddInstructor>
        </div>

        <!-- Edit Modal Overlay -->
        <div v-if="editingInstructor" style="position: fixed; inset: 0; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 100;">
            <div class="glass-panel" style="background: white; padding: 2rem; width: 100%; max-width: 500px;">
                <h3 style="margin-bottom: 1.5rem;">Edit Instructor</h3>
                <form @submit.prevent="submitEdit" style="display: grid; gap: 1rem; padding: 0; margin-bottom: 0;">
                    <div>
                        <label for="name">Name</label>
                        <input id="name" v-model="editingInstructor.name" placeholder="Name" required />
                    </div>
                    <div>
                        <label for="surname">Surname</label>
                        <input id="surname" v-model="editingInstructor.surname" placeholder="Surname" required />
                    </div>
                    <div>
                        <label for="email">Email</label>
                        <input id="email" v-model="editingInstructor.email" placeholder="Email" required />
                    </div>
                    <div>
                        <label for="dept">Department</label>
                        <input id="dept" v-model="editingInstructor.department" placeholder="Department" required />
                    </div>
                    <div>
                        <label for="hdate">Hiring Date</label>
                        <input id="hdate" v-model="editingInstructor.hiringDate" type="date" required />
                    </div>
                    <div style="display: flex; gap: 1rem; margin-top: 1rem;">
                        <button type="submit" class="btn-primary" style="flex: 1;">Save Changes</button>
                        <button type="button" class="btn-secondary" style="flex: 1;" @click="cancelEdit">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import AddInstructor from "./AddInstructor.vue"
import api from "../services/api"

// --- Reactive State ---
const instructors = ref([])
const editingInstructor = ref(null)

// --- Core Logic ---

const refreshInstructors = async () => {
    try {
        const res = await api.getInstructors()
        instructors.value = res.data
    } catch (e) {
        console.error("Error fetching instructors:", e)
    }
}

function startEdit(instructor){
    editingInstructor.value = {...instructor}
}

function cancelEdit(){
    editingInstructor.value = null
}

async function submitEdit(){
    try {
        await api.updateInstructor(editingInstructor.value.id, editingInstructor.value)
        await refreshInstructors()
        editingInstructor.value = null
        // alert('Instructor updated!')
    } catch (e) {
        console.error('Error updating instructor:', e)
        alert('Error updating instructor.')
    }
}

async function deleteInstructor(id) {
  if (!confirm('Are you sure you want to delete this instructor?')) return
  try {
    await api.deleteInstructor(id)
    await refreshInstructors()
  } catch (e) {
    console.error('Error deleting instructor:', e)
    alert('Error deleting instructor.')
  }
}

// --- Lifecycle Hooks ---
onMounted(refreshInstructors)
</script>