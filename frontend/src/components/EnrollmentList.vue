<template>
  <div class="glass-panel" style="padding: 2rem;">
    <div class="page-header">
      <h2>Enrollment Management</h2>
    </div>

    <!-- Filter Section -->
    <div class="glass-panel" style="padding: 1.5rem; margin-bottom: 2rem; background: rgba(255,255,255,0.3)">
      <h3 style="margin-bottom: 1rem; font-size: 1.2rem;">Filters</h3>
      <form @submit.prevent="applyFilter" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 1rem; padding: 0; margin: 0; max-width: none;">
          <input 
            v-model="filters.studentId" 
            placeholder="Student ID" 
            type="number"
          />
          <input 
            v-model="filters.courseId" 
            placeholder="Course ID" 
            type="number"
          />
        <div style="display: flex; gap: 0.5rem; align-items: center;">
          <button type="submit" class="btn-primary">Apply Filter</button>
          <button type="button" class="btn-secondary" @click="resetFilter">Reset</button>
        </div>
      </form>
    </div>

    <!-- Enrollment Form -->
    <div class="glass-panel" style="padding: 1.5rem; margin-bottom: 2rem; background: rgba(255,255,255,0.4);">
      <h3 style="margin-top: 0; margin-bottom: 1.5rem;">Add New Enrollment</h3>
      <form @submit.prevent="addEnrollment" style="max-width: none; padding: 0; margin: 0; display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
          <input 
            v-model="newEnrollment.studentId" 
            placeholder="Student ID" 
            type="number"
            required
          />
          <input 
            v-model="newEnrollment.courseId" 
            placeholder="Course ID" 
            type="number"
            required
          />
          <input 
            v-model="newEnrollment.grade" 
            placeholder="Grade (Optional)" 
            type="number"
            min="0"
            max="100"
          />
        <div style="grid-column: 1 / -1; display: flex; justify-content: flex-end; margin-top: 0.5rem;">
          <button type="submit" class="btn-primary" style="width: auto;">Enroll Student</button>
        </div>
      </form>
    </div>

    <!-- Enrollments Table -->
    <div class="table-container">
      <table class="enrollments-table-content">
        <thead>
          <tr>
            <th>Student ID</th>
            <th>Student Name</th>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Instructor</th>
            <th>Grade</th>
            <th>Enrolled At</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enrollment in enrollments" :key="enrollment.id">
            <td>{{ enrollment.studentId }}</td>
            <td>{{ enrollment.studentName }} {{ enrollment.studentSurname }}</td>
            <td>{{ enrollment.courseId }}</td>
            <td>{{ enrollment.courseName }}</td>
            <td>{{ enrollment.instructorName }} {{ enrollment.instructorSurname }}</td>
            <td>
              <input 
                type="number" 
                v-model="enrollment.grade" 
                min="0" 
                max="100"
                style="width: 80px; padding: 0.4rem;"
                @change="updateGrade(enrollment)">
            </td>
            <td>{{ new Date(enrollment.enrolledAt).toLocaleDateString() }}</td>
            <td>
              <button 
                class="btn-danger"
                style="padding: 0.4rem 0.8rem; font-size: 0.85rem;"
                @click="deleteEnrollment(enrollment.id)"> Remove </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api'

// --- Reactive State ---
const enrollments = ref([])
const filters = ref({
  studentId: '',
  courseId: ''
})
const newEnrollment = ref({
  studentId: '',
  courseId: '',
  grade: null
})

// --- Core Logic ---

const fetchEnrollments = async () => {
  try {
    const res = await api.getEnrollments(filters.value)
    enrollments.value = res.data
  } catch (error) {
    console.error('Error fetching enrollments:', error)
    // alert('Failed to fetch enrollments')
  }
}

async function addEnrollment(){
  try {
    const res = await api.createEnrollment(newEnrollment.value)
    if (res.status === 201) {
      await fetchEnrollments()
      newEnrollment.value = { studentId: '', courseId: '', grade: null }
      alert('Student enrolled successfully!')
    } else {
      alert('Failed to enroll student.')
    }
  } catch (error) {
    console.error('Error enrolling student:', error)
    alert('Failed to enroll student. Check IDs.')
  }
}

async function deleteEnrollment(enrollmentId) {
  if (!confirm("Are you sure you want to remove this enrollment?")) return;
  try {
    const res = await api.deleteEnrollment(enrollmentId)
    if (res.status === 200) {
      await fetchEnrollments()
      // alert('Enrollment deleted successfully!')
    } else {
      alert('Failed to delete enrollment')
    }
  } catch (e) {
    console.error('Error deleting enrollment:', e)
    alert('Error deleting enrollment')
  }
}

async function updateGrade(enrollment){
  try{
    const res = await api.updateEnrollmentGrade(enrollment.id, enrollment.grade)
    if (res.status === 200) {
      // await fetchEnrollments() // Optional: refresh to be sure
      alert('Grade updated!')
    } else {
      alert('Failed to update grade')
    }
  } catch (e) {
    console.error('Error updating grade:', e)
    alert('Error updating grade')
  }
}

// --- Filter Handling ---

function applyFilter(){
  fetchEnrollments()
}

function resetFilter(){
  filters.value = {studentId : "", courseId : ""}
  fetchEnrollments()
}

// --- Lifecycle Hooks ---
onMounted(fetchEnrollments)
</script>
