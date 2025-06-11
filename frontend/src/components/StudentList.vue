<template>
  <div class="container">
    <div class="list">
      <h2>Students</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="student in students"
            :key="student.studentId"
            @click="selectStudent(student)"
          >
            <td>{{ student.studentId }}</td>
            <td>{{ student.name }}</td>
            <td>{{ student.surname }}</td>
            <td>{{ student.email }}</td>
            <td><button @click.stop="removeStudent(student.studentId)">Delete</button></td>
          </tr>
        </tbody>
      </table>
      <div class="add-form">
        <h3>Add Student</h3>
        <input v-model="newStudent.name" placeholder="Name" />
        <input v-model="newStudent.surname" placeholder="Surname" />
        <input v-model="newStudent.email" placeholder="Email" />
        <input
          v-model="newStudent.birthDate"
          placeholder="Birth Date (YYYY-MM-DD)"
        />
        <input
          v-model.number="newStudent.advisorId"
          placeholder="Advisor ID"
          type="number"
        />
        <button @click="addStudent">Add</button>
      </div>
    </div>
    <div class="detail" v-if="selectedStudent">
      <h3>
        Courses for {{ selectedStudent.name }} {{ selectedStudent.surname }}
      </h3>
      <table>
        <thead>
          <tr>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enr in enrollments" :key="enr.id">
            <td>{{ enr.courseId }}</td>
            <td>{{ enr.courseName }}</td>
            <td>
              <input
                type="number"
                v-model.number="enr.grade"
                @change="updateGrade(enr)"
              />
            </td>
          </tr>
        </tbody>
      </table>
      <div class="enroll-form">
        <h4>Enroll in Course</h4>
        <input v-model.number="enrollCourseId" placeholder="Course ID" />
        <button @click="enroll">Enroll</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

const students = ref([])
const selectedStudent = ref(null)
const enrollments = ref([])
const newStudent = ref({
  name: '',
  surname: '',
  email: '',
  birthDate: '',
  advisorId: null
})
const enrollCourseId = ref(null)

const selectStudent = async (student) => {
  selectedStudent.value = student
  enrollments.value = []
  try {
    const res = await api.get('/enrollment/student', {
      params: { studentId: student.studentId }
    })
    enrollments.value = res.data
  } catch (err) {
    console.error('Failed to fetch enrollments', err)
  }
}

const addStudent = async () => {
  try {
    await api.post('/student', { ...newStudent.value, studentId: 0 })
    const res = await api.get('/student')
    students.value = res.data
    newStudent.value = {
      name: '',
      surname: '',
      email: '',
      birthDate: '',
      advisorId: null
    }
  } catch (err) {
    console.error('Failed to add student', err)
  }
}

const enroll = async () => {
  if (!selectedStudent.value) return
  try {
    await api.post('/enrollment', {
      studentId: selectedStudent.value.studentId,
      courseId: enrollCourseId.value
    })
    const res = await api.get('/enrollment/student', {
      params: { studentId: selectedStudent.value.studentId }
    })
    enrollments.value = res.data
    enrollCourseId.value = null
  } catch (err) {
    console.error('Failed to enroll', err)
  }
}

const updateGrade = async (enr) => {
  try {
    await api.patch(`/enrollment/${enr.id}/grade`, null, {
      params: { grade: enr.grade }
    })
  } catch (err) {
    console.error('Failed to update grade', err)
  }
}

const removeStudent = async (id) => {
  try {
    await api.delete(`/student/${id}`)
    const res = await api.get('/student')
    students.value = res.data
    if (selectedStudent.value && selectedStudent.value.studentId === id) {
      selectedStudent.value = null
      enrollments.value = []
    }
  } catch (err) {
    console.error('Failed to delete student', err)
  }
}

  onMounted(async () => {
    try {
      const response = await api.get('/student')
      students.value = response.data
    } catch (err) {
      console.error('Failed to fetch students', err)
    }
  })
</script>

<style scoped>
.container {
  display: flex;
  gap: 20px;
}

.list {
  width: 45%;
}

.detail {
  flex: 1;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ccc;
  padding: 4px 8px;
  text-align: left;
}

button {
  cursor: pointer;
}
</style>
