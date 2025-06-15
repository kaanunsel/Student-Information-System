<template>
    <div>
        <h2>Student List</h2>
        <form @submit.prevent="applyFilter">
        <input v-model="filters.id" placeholder="ID" type="number" />
        <input v-model="filters.name" placeholder="Name" />
        <input v-model="filters.surname" placeholder="Surname" />
        <button type="submit">Apply Filter</button>
        <button type="button" @click="resetFilter">Reset Filter</button>
        </form>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Birth Date</th>
                    <th>Advisor ID</th>
                    <th>Advisor Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
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
                    <td><button @click="startEdit(student)">Edit</button></td>
                    <td><button @click="deleteStudent(student.studentId)">Delete</button></td>
                </tr>
            </tbody>
        </table>
        <AddStudent @student-added="refreshStudents"></AddStudent>
        <div v-if="editingStudent">
            <h3>Edit Student</h3>
            <form @submit.prevent="submitEdit">
                <div>
                    <label for="name">Name:</label>
                    <input id="name" v-model="editingStudent.name" placeholder="Name" required/>
                </div>
                <div>
                    <label for="surname">Surname:</label>
                    <input id="surname" v-model="editingStudent.surname" placeholder="Surname" required />
                </div>
                <div>
                    <label for="email">Email:</label>
                    <input id="email" v-model="editingStudent.email" placeholder="Email" required />
                </div>
                <div>
                    <label for="birthDate">Birth Date:</label>
                    <input id="birthDate" v-model="editingStudent.birthDate" type="date" required />
                </div>
                <div>
                    <label for="advisorId">Advisor ID:</label>
                    <input id="advisorId" v-model="editingStudent.advisorId" type="number" placeholder="Advisor ID" required />
                </div>
                <button type="submit">Save</button>
                <button type="button" @click="cancelEdit">Cancel</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted} from "vue"
import AddStudent from "./AddStudent.vue"

// --- Reactive State ---

// Holds the list of students fetched from the backend.
const students = ref([])
// Holds the student object currently being edited, or null if not in edit mode.
const editingStudent = ref(null)
// Holds the current filter values for querying the student list.
const filters = ref({
    id: null,
    name: "",
    surname: ""
})

// --- Core Logic ---

/**
 * Fetches the list of students from the backend, applying any active filters.
 */
const refreshStudents = async () => {
    const queryParams = new URLSearchParams()
    if (filters.value.id) queryParams.append("id", filters.value.id)
    if (filters.value.name) queryParams.append('name', filters.value.name)
    if (filters.value.surname) queryParams.append('surname', filters.value.surname)
    const res = await fetch(`http://localhost:8080/student?${queryParams.toString()}`)
    students.value = await res.json()
}

/**
 * Initiates the editing process for a student.
 * @param {object} student The student object to be edited.
 */
function startEdit(student) {
  editingStudent.value = { ...student }
}

/**
 * Cancels the editing process and clears the editing state.
 */
function cancelEdit() {
    editingStudent.value = null
}

/**
 * Submits the updated student data to the backend.
 */
async function submitEdit() {
    try {
        const res = await fetch(`http://localhost:8080/student/${editingStudent.value.studentId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(editingStudent.value)
        })
        if (res.ok) {
            await refreshStudents()
            editingStudent.value = null
            alert("Student updated!")
        } else {
            alert("Failed to update student.")
        }
    } catch (e) {
        console.error("Error updating student:", e)
        alert("Error updating student.")
    }
}

/**
 * Deletes a student after confirming with the user.
 * @param {number} studentId The ID of the student to be deleted.
 */
async function deleteStudent(studentId) {
    if (!confirm("Are you sure you want to delete this student?")) return;
    try {
        const res = await fetch(`http://localhost:8080/student/${studentId}`, {
        method: 'DELETE'
        })
        if(res.ok) {
            await refreshStudents()
            alert("Student deleted!")
        } else {
            alert('Failed to delete student.')
        }
    }catch (e) {
        console.error("Error deleting student:", e)
        alert('Error deleting student.')
    }
}

// --- Filter Handling ---

/**
 * Applies the current filters by re-fetching the student list.
 */
function applyFilter(){
    refreshStudents()
}

/**
 * Resets all filters to their default state and re-fetches the student list.
 */
function resetFilter(){
    filters.value = { id: null, name: '', surname: '' }
    refreshStudents()
}

// --- Lifecycle Hooks ---

// Fetches the initial list of students when the component is mounted.
onMounted(refreshStudents)

// Exposes the refreshStudents function to be called from parent components.
defineExpose({ refreshStudents })
</script>