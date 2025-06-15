<template>
    <div>
        <h2>Course List</h2>
        <form @submit.prevent="applyFilter">
            <input v-model="filters.id" placeholder="ID" type="number" />
            <input v-model="filters.name" placeholder="Name" />
            <input v-model="filters.code" placeholder="Code" />
            <input v-model="filters.instructorId" placeholder="Instructor ID" type="number" />
            <button type="submit">Filter</button>
            <button type="button" @click="resetFilter">Cancel</button>
        </form>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
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
                <tr v-for="course in courses" :key="course.id">
                    <td>{{ course.id }}</td>
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
                    <input id="insId" v-model="editingCourse.instructorId" type="number" placeholder="Instructor ID" required/>
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

// --- Reactive State ---

// Holds the list of courses fetched from the backend.
const courses = ref([])
// Holds the course object currently being edited, or null if not in edit mode.
const editingCourse = ref(null)
// Holds the current filter values for querying the course list.
const filters = ref({
    id: null,
    name: '',
    code: '',
    instructorId: null
})

// --- Core Logic ---

/**
 * Fetches the list of courses from the backend, applying any active filters.
 */
const refreshCourses = async () => {
    const queryParams = new URLSearchParams()
    if (filters.value.id) queryParams.append('id', filters.value.id)
    if (filters.value.name) queryParams.append('name', filters.value.name)
    if (filters.value.code) queryParams.append('code', filters.value.code)
    if (filters.value.instructorId) queryParams.append('instructorId', filters.value.instructorId)
    const res = await fetch(`http://localhost:8080/course?${queryParams.toString()}`)
    courses.value = await res.json()
}

/**
 * Initiates the editing process for a course.
 * @param {object} course The course object to be edited.
 */
function startEdit(course){
    editingCourse.value = {...course}
}

/**
 * Cancels the editing process and clears the editing state.
 */
function cancelEdit(){
    editingCourse.value = null
}

/**
 * Submits the updated course data to the backend.
 */
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
        console.error("Error updating course:", e)
        alert("Error updating course.")
    }
}

/**
 * Deletes a course after confirming with the user.
 * @param {string} code The code of the course to be deleted.
 */
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
    console.error("Error deleting course:", e)
    alert('Error deleting course.')
  }
}

// --- Filter Handling ---

/**
 * Applies the current filters by re-fetching the course list.
 */
function applyFilter() {
    refreshCourses()
}

/**
 * Resets all filters to their default state and re-fetches the course list.
 */
function resetFilter() {
    filters.value = { id: null, name: '', code: '', instructorId: null }
    refreshCourses()
}

// --- Lifecycle Hooks ---

// Fetches the initial list of courses when the component is mounted.
onMounted(refreshCourses)

// Exposes the refreshCourses function to be called from parent components.
defineExpose({ refreshCourses })
</script>