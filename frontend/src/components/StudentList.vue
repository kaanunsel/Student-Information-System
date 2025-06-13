<template>
    <div>
        <h2>Student List</h2>
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
                <input v-model="editingStudent.name" placeholder="Name" required/>
                <input v-model="editingStudent.surname" placeholder="Surname" required />
                <input v-model="editingStudent.email" placeholder="Email" required />
                <input v-model="editingStudent.birthDate" type="date" required />
                <input v-model="editingStudent.advisorId" type="number" placeholder="Advisor ID" required />
                <button type="submit">Save</button>
                <button type="button" @click="cancelEdit">Cancel</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted} from "vue"
import AddStudent from "./AddStudent.vue"

const students = ref([])
const editingStudent = ref(null)

const refreshStudents = async () => {
    const res = await fetch('http://localhost:8080/student')
    students.value = await res.json()
}

function startEdit(student) {
  editingStudent.value = { ...student }
}

function cancelEdit() {
    editingStudent.value = null
}

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
        alert("Error updating student.")
    }
}

async function deleteStudent(studentId) {
    if (!confirm("ARE YOU SURE????")) return;
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
        alert('Error deleting student.')
    }
}

onMounted(refreshStudents)

// Expose the refreshStudents function to the parent via ref
defineExpose({ refreshStudents })
</script>