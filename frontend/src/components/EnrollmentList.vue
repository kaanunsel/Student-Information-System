<template>
  <div class="enrollment-list">
    <h2>Enrollment Management</h2>

    <!-- Filter Section -->
    <div class="filter-section">
      <form @submit.prevent="applyFilter">
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
        <button type="submit">Apply Filter</button>
        <button type="button" @click="resetFilter">Reset Filter</button>
      </form>
    </div>

    <!-- Enrollment Form -->
    <div class="enrollment-form">
      <h2>Add New Enrollment</h2>
      <form @submit.prevent="addEnrollment">
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
            placeholder="Grade" 
            type="number"
          />
        <button type="submit">Enroll Student</button>
      </form>
    </div>

    <!-- Enrollments Table -->
    <div class="enrollments-table">
      <h2>Current Enrollments</h2>
      <table>
        <thead>
          <tr>
            <th>Student ID</th>
            <th>Student Name</th>
            <th>Course ID</th>
            <th>Course Name</th>
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
            <td>
              <input 
                type="number" 
                v-model="enrollment.grade" 
                min="0" 
                max="100"
                @change="updateGrade(enrollment)">
            </td>
            <td>{{ enrollment.enrolledAt }}</td>
            <td>
              <button 
                @click="deleteEnrollment(enrollment.id)"> Remove </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'


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

async function addEnrollment(){
  try {
    const response = await axios.post("http://localhost:8080/enrollment", newEnrollment.value)
    if (response.status === 201) {
      await fetchEnrollments()
      newEnrollment.value = { studentId: '', courseId: '' }
      alert('Student enrolled successfully!')
    } else {
      alert('Failed to enroll student')
    }
  } catch (error) {
    console.error('Error enrolling student:', error)
    alert('Failed to enroll student')
  }
}

async function deleteEnrollment(enrollmentId) {
  if (!confirm("ARE YOU SURE????")) return;
  try {
    const response = await axios.delete(`http://localhost:8080/enrollment/${enrollmentId}`)
    if (response.status === 200) {
      await fetchEnrollments()
      alert('Enrollment deleted successfully!')
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
    const response = await axios.patch(`http://localhost:8080/enrollment/${enrollment.id}/grade?grade=${enrollment.grade}`)
    if (response.status === 200) {
      await fetchEnrollments()
      alert('Grade updated successfully!')
    } else {
      alert('Failed to update grade')
    }
  } catch (e) {
    console.error('Error updating grade:', e)
    alert('Error updating grade')
  }
  }

function applyFilter(){
  fetchEnrollments()
}

function resetFilter(){
  filters.value = {studentId : "", courseId : ""}
  fetchEnrollments()
}

const fetchEnrollments = async () => {
  const queryParams = new URLSearchParams()
  if(filters.value.studentId) queryParams.append("studentId",filters.value.studentId)
  if(filters.value.courseId) queryParams.append("courseId",filters.value.courseId)
  try {
    const response = await axios.get(`http://localhost:8080/enrollment?${queryParams.toString()}`)
    enrollments.value = response.data
  } catch (error) {
    console.error('Error fetching enrollments:', error)
    alert('Failed to fetch enrollments')
  }
}

onMounted(fetchEnrollments)
</script>
