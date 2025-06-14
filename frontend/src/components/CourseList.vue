<template>
    <div>
        <h2>Course List</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Code</th>
                    <th>Credit</th>
                    <th>Instructor ID</th>
                    <th>Instructor Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="course in courses" :key="course.courseId">
                    <td>{{ course.name }}</td>
                    <td>{{ course.code }}</td>
                    <td>{{ course.credit }}</td>
                    <td>{{ course.instructorId }}</td>
                    <td>{{ course.instructorName }}</td>
                    <td><button @click="startEdit(course)">Edit</button></td>
                    <td><button @click="deleteCourse(course.code)">Delete</button></td>
                </tr>
            </tbody>
        </table>
        <AddCourse @course-added="refreshCourses"></AddCourse>
        <div v-if="editingCourse">
            <h3>Edit Course</h3>
            <form @submit.prevent="submitEdit">
                <div>
                    <label for="name">Name:</label>
                    <input id="name" v-model="editingCourse.name" placeholder="Name" required/>
                </div>
                <div>
                    <label for="code">Code:</label>
                    <input id="code" v-model="editingCourse.code" placeholder="Code" required />
                </div>
                <div>
                    <label for="credit">Credit:</label>
                    <input id="credit" v-model="editingCourse.credit" type="number" placeholder="Credit" required />
                </div>
                <div>
                    <label for="insId">Instructor ID:</label>
                    <input id="insId" v-model="editingCourse.instructorId" type="number" placeholder="Instructor ID" />
                </div>
                <div>
                    <label for="insName">Instructor Name:</label>
                    <input id="insName" v-model="editingCourse.instructorName" placeholder="Instructor Name" required />
                </div>
                <div>
                    <button type="submit">Save</button>
                    <button type="button" @click="cancelEdit">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>

import { ref, onMounted } from "vue"
import AddCourse from "./AddCourse.vue"

const courses = ref([])
const editingCourse = ref(null)

const refreshCourses = async () => {
    const res = await fetch('http://localhost:8080/course')
    courses.value = await res.json()
}

function startEdit(course){
    editingCourse.value = {...course}
}

function cancelEdit(){
    editingCourse.value = null
}

async function submitEdit(){
    try {
        const res = await fetch(`http://localhost:8080/course/${editingCourse.value.code}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(editingCourse.value)
        })
        if(res.ok){
            await refreshCourses()
            editingCourse.value = null
            alert("Course updated succesfully!")
        } else {
            alert("Failed to update course.")
        }
    } catch (e) {
        alert("Error updating course.")
    }
}

async function deleteCourse(code) {
  if (!confirm('Are you sure you want to delete this course?')) return
  try {
    const res = await fetch(`http://localhost:8080/course/${code}`, {
      method: 'DELETE'
    })
    if (res.ok) {
      await refreshCourses()
      alert('Course deleted!')
    } else {
      alert('Failed to delete course.')
    }
  } catch (e) {
    alert('Error deleting course.')
  }
}

onMounted(refreshCourses)

defineExpose({ refreshCourses })
</script>