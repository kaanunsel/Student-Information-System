<template>
    <div class="glass-panel" style="padding: 2rem;">
        <div class="page-header">
            <h2>Course List</h2>
        </div>

        <div class="glass-panel" style="padding: 1.5rem; margin-bottom: 2rem; background: rgba(255,255,255,0.3)">
            <h3 style="margin-bottom: 1rem; font-size: 1.2rem;">Filters</h3>
            <form @submit.prevent="applyFilter" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 1rem; padding: 0; margin: 0; max-width: none;">
                <input v-model="filters.id" placeholder="ID" type="number" />
                <input v-model="filters.name" placeholder="Name" />
                <input v-model="filters.code" placeholder="Code" />
                <input v-model="filters.instructorId" placeholder="Instructor ID" type="number" />
                <div style="display: flex; gap: 0.5rem; align-items: center;">
                    <button type="submit" class="btn-primary">Apply Filter</button>
                    <button type="button" class="btn-secondary" @click="resetFilter">Reset</button>
                </div>
            </form>
        </div>

        <div class="table-container">
            <table class="course-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Code</th>
                        <th>Credit</th>
                        <th>Instructor ID</th>
                        <th>Instructor Name</th>
                        <th>Actions</th>
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
                        <td>
                            <div style="display: flex; gap: 0.5rem;">
                                <button class="btn-secondary" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="startEdit(course)">Edit</button>
                                <button class="btn-danger" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;" @click="deleteCourse(course.code)">Delete</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div style="margin-top: 2rem;">
            <AddCourse @course-added="refreshCourses"></AddCourse>
        </div>

        <!-- Edit Modal Overlay -->
        <div v-if="editingCourse" style="position: fixed; inset: 0; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 100;">
            <div class="glass-panel" style="background: white; padding: 2rem; width: 100%; max-width: 500px;">
                <h3 style="margin-bottom: 1.5rem;">Edit Course</h3>
                <form @submit.prevent="submitEdit" style="display: grid; gap: 1rem; padding: 0; margin-bottom: 0;">
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
import AddCourse from "./AddCourse.vue"
import api from "../services/api"

// --- Reactive State ---
const courses = ref([])
const editingCourse = ref(null)
const originalCode = ref(null)
const filters = ref({
    id: null,
    name: '',
    code: '',
    instructorId: null
})

// --- Core Logic ---

const refreshCourses = async () => {
    try {
        const res = await api.getCourses(filters.value)
        courses.value = res.data
    } catch (e) {
        console.error("Error fetching courses:", e)
    }
}

function startEdit(course){
    editingCourse.value = {...course}
    originalCode.value = course.code
}

function cancelEdit(){
    editingCourse.value = null
}

async function submitEdit(){
    try {
        // API expects code as identifier for update in current structure
        // DTO validation will happen on backend
        await api.updateCourse(originalCode.value, editingCourse.value)
        await refreshCourses()
        editingCourse.value = null
        // alert("Course updated succesfully!")
    } catch (e) {
        console.error("Error updating course:", e)
        alert("Error updating course.")
    }
}

async function deleteCourse(code) {
  if (!confirm('Are you sure you want to delete this course?')) return
  try {
    await api.deleteCourse(code)
    await refreshCourses()
  } catch (e) {
    console.error("Error deleting course:", e)
    alert('Error deleting course.')
  }
}

// --- Filter Handling ---

function applyFilter() {
    refreshCourses()
}

function resetFilter() {
    filters.value = { id: null, name: '', code: '', instructorId: null }
    refreshCourses()
}

// --- Lifecycle Hooks ---
onMounted(refreshCourses)
defineExpose({ refreshCourses })
</script>