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
            <th></th>
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
            <td>
              <button @click="removeEnrollment(enr.id)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="enroll-form">
        <h4>Enroll in Course</h4>
        <input v-model.number="enrollCourseId" placeholder="Course ID" />
        <button @click="enroll">Enroll</button>
      </div>
      <div class="edit-form">
        <h4>Edit Student</h4>
        <input v-model="editStudent.name" placeholder="Name" />
        <input v-model="editStudent.surname" placeholder="Surname" />
        <input v-model="editStudent.email" placeholder="Email" />
        <input v-model="editStudent.birthDate" placeholder="Birth Date" />
        <input
          v-model.number="editStudent.advisorId"
          placeholder="Advisor ID"
          type="number"
        />
        <button @click="updateStudent">Save</button>
      </div>
    </div>
  </div>
</template>

<script setup>
// Vue component responsible for displaying and managing students
// This file demonstrates CRUD operations as well as enrollment management
// for each student.
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

// list of all students fetched from the API
const students = ref([])
// currently selected student whose details are shown
const selectedStudent = ref(null)
// enrollment list for the selected student
const enrollments = ref([])
// editable copy of the selected student
const editStudent = ref({})
// model for the "add student" form
const newStudent = ref({
  name: '',
  surname: '',
  email: '',
  birthDate: '',
  advisorId: null
})
// course id input used when enrolling the selected student into a course
const enrollCourseId = ref(null)

// fetch enrollments for the clicked student and prepare edit form
const selectStudent = async (student) => {
  selectedStudent.value = student
  // create a shallow copy for editing so we don't mutate the table list
  editStudent.value = { ...student }
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

// call API to create a new student
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

// enroll the currently selected student into a new course
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

// update grade for a particular enrollment record
const updateGrade = async (enr) => {
  try {
    await api.patch(`/enrollment/${enr.id}/grade`, null, {
      params: { grade: enr.grade }
    })
  } catch (err) {
    console.error('Failed to update grade', err)
  }
}

// remove an enrollment completely
const removeEnrollment = async (enrId) => {
  try {
    await api.delete(`/enrollment/${enrId}`)
    // refresh enrollment list
    const res = await api.get('/enrollment/student', {
      params: { studentId: selectedStudent.value.studentId }
    })
    enrollments.value = res.data
  } catch (err) {
    console.error('Failed to remove enrollment', err)
  }
}

// persist edits to the selected student
const updateStudent = async () => {
  try {
    await api.put(`/student/${selectedStudent.value.studentId}`, editStudent.value)
    const res = await api.get('/student')
    students.value = res.data
    // update selectedStudent reference from refreshed list
    const found = students.value.find(s => s.studentId === selectedStudent.value.studentId)
    if (found) {
      selectedStudent.value = found
      editStudent.value = { ...found }
    }
  } catch (err) {
    console.error('Failed to update student', err)
  }
}

// delete a student by id and refresh list
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

  // initial fetch of all students when component is created
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
  width: 90%;
  margin: auto;
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
  background: #fff;
}

th,
td {
  border: 1px solid #ccc;
  padding: 6px 8px;
  text-align: left;
}

tr:hover {
  background: #f1f1f1;
}

button {
  cursor: pointer;
}
</style>
