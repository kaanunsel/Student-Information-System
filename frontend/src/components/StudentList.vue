<template>
    <div class="glass-panel" style="padding: 2rem;">
        <div class="page-header">
            <h2>Student List</h2>
            <div style="display: flex; gap: 0.5rem">
                <!-- Add Student Button could go here or keep component below -->
            </div>
        </div>

        <div class="glass-panel" style="padding: 1.5rem; margin-bottom: 2rem; background: rgba(255,255,255,0.3)">
            <h3 style="margin-bottom: 1rem; font-size: 1.2rem;">Filters</h3>
            <form @submit.prevent="applyFilter" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 1rem; padding: 0; margin: 0; max-width: none;">
                <input v-model="filters.id" placeholder="ID" type="number" />
                <input v-model="filters.name" placeholder="Name" />
                <input v-model="filters.surname" placeholder="Surname" />
                <div style="display: flex; gap: 0.5rem; align-items: center;">
                    <button type="submit" class="btn-primary">Apply Filter</button>
                    <button type="button" class="btn-secondary" @click="resetFilter">Reset</button>
                </div>
            </form>
        </div>

        <div class="table-container">
            <table class="student-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Birth Date</th>
                        <th>Advisor ID</th>
                        <th>Advisor Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="student in students" :key="student.studentId">
                        <td>{{ student.studentId }}</td>
                        <td>{{ student.name }}</td>
                        <td>{{ student.surname }}</td>
                        <td>{{ student.email }}</td>
                        <td>{{ student.birthDate }}</td>
                        <td>{{ student.advisorId }}</td>
                        <td>{{ student.advisorName }}</td>
                        <td>
                            <div style="display: flex; gap: 0.5rem;">
                                <button class="btn-secondary" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="startEdit(student)">Edit</button>
                                <button class="btn-danger" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="deleteStudent(student.studentId)">Delete</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div style="margin-top: 2rem;">
            <AddStudent @student-added="refreshStudents"></AddStudent>
        </div>

        <!-- Edit Modal Overlay -->
        <div v-if="editingStudent" style="position: fixed; inset: 0; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 100;">
            <div class="glass-panel" style="background: white; padding: 2rem; width: 100%; max-width: 500px;">
                <h3 style="margin-bottom: 1.5rem;">Edit Student</h3>
                <form @submit.prevent="submitEdit" style="display: grid; gap: 1rem; padding: 0; margin-bottom: 0;">
                    <div>
                        <label for="name">Name</label>
                        <input id="name" v-model="editingStudent.name" placeholder="Name" required/>
                    </div>
                    <div>
                        <label for="surname">Surname</label>
                        <input id="surname" v-model="editingStudent.surname" placeholder="Surname" required />
                    </div>
                    <div>
                        <label for="email">Email</label>
                        <input id="email" v-model="editingStudent.email" placeholder="Email" required />
                    </div>
                    <div>
                        <label for="birthDate">Birth Date</label>
                        <input id="birthDate" v-model="editingStudent.birthDate" type="date" required />
                    </div>
                    <div>
                        <label for="advisorId">Advisor ID</label>
                        <input id="advisorId" v-model="editingStudent.advisorId" type="number" placeholder="Advisor ID" required />
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
import AddStudent from "./AddStudent.vue"
import api from "../services/api"

// --- Reactive State ---
const students = ref([])
const editingStudent = ref(null)
const filters = ref({
    id: null,
    name: "",
    surname: ""
})

// --- Core Logic ---

const refreshStudents = async () => {
    try {
        const res = await api.getStudents(filters.value)
        students.value = res.data
    } catch (e) {
        console.error("Error fetching students:", e)
    }
}

function startEdit(student) {
  editingStudent.value = { ...student }
}

function cancelEdit() {
    editingStudent.value = null
}

async function submitEdit() {
    try {
        const studentId = editingStudent.value.studentId;
        // API expects just the fields to update, but we can send the whole DTO
        await api.updateStudent(studentId, editingStudent.value)
        await refreshStudents()
        editingStudent.value = null
        // Could add toast notification here
    } catch (e) {
        console.error("Error updating student:", e)
        alert("Error updating student. Please check inputs.")
    }
}

async function deleteStudent(studentId) {
    if (!confirm("Are you sure you want to delete this student?")) return;
    try {
        await api.deleteStudent(studentId)
        await refreshStudents()
    }catch (e) {
        console.error("Error deleting student:", e)
        alert('Error deleting student.')
    }
}

// --- Filter Handling ---

function applyFilter(){
    refreshStudents()
}

function resetFilter(){
    filters.value = { id: null, name: '', surname: '' }
    refreshStudents()
}

// --- Lifecycle Hooks ---
onMounted(refreshStudents)
defineExpose({ refreshStudents })
</script>