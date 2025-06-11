<template>
  <div class="container">
    <div class="list">
      <h2>Courses</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Code</th>
            <th>Credit</th>
            <th>Instructor</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="course in courses"
            :key="course.courseId"
            @click="selectCourse(course)"
          >
            <td>{{ course.courseId }}</td>
            <td>{{ course.name }}</td>
            <td>{{ course.code }}</td>
            <td>{{ course.credit }}</td>
            <td>{{ course.instructorName }}</td>
            <td><button @click.stop="removeCourse(course.courseId)">Delete</button></td>
          </tr>
        </tbody>
      </table>
      <div class="add-form">
        <h3>Add Course</h3>
        <input v-model="newCourse.name" placeholder="Name" />
        <input v-model="newCourse.code" placeholder="Code" />
        <input type="number" v-model.number="newCourse.credit" placeholder="Credit" />
        <input type="number" v-model.number="newCourse.instructorId" placeholder="Instructor ID" />
        <input v-model="newCourse.instructorName" placeholder="Instructor Name" />
        <button @click="addCourse">Add</button>
      </div>
    </div>
    <div class="detail" v-if="selectedCourse">
      <h3>Students in {{ selectedCourse.name }}</h3>
      <table>
        <thead>
          <tr>
            <th>Student ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enr in enrollments" :key="enr.id">
            <td>{{ enr.studentId }}</td>
            <td>{{ enr.studentName }}</td>
            <td>{{ enr.studentSurname }}</td>
            <td>{{ enr.grade }}</td>
          </tr>
        </tbody>
      </table>
      <div class="edit-form">
        <h4>Edit Course</h4>
        <input v-model="editCourse.name" placeholder="Name" />
        <input v-model="editCourse.code" placeholder="Code" />
        <input type="number" v-model.number="editCourse.credit" placeholder="Credit" />
        <input type="number" v-model.number="editCourse.instructorId" placeholder="Instructor ID" />
        <input v-model="editCourse.instructorName" placeholder="Instructor Name" />
        <button @click="updateCourse">Save</button>
      </div>
    </div>
  </div>
</template>

<script setup>
// Component for viewing and editing course information
import { ref, onMounted } from 'vue'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

// array holding all courses returned from backend
const courses = ref([])
// course currently selected from the list
const selectedCourse = ref(null)
// enrollments of the selected course
const enrollments = ref([])
// editable copy of the selected course
const editCourse = ref({})
// model for add course form
const newCourse = ref({
  name: '',
  code: '',
  credit: null,
  instructorId: null,
  instructorName: ''
})

// when a course row is clicked fetch its enrollments
const selectCourse = async (course) => {
  selectedCourse.value = course
  editCourse.value = { ...course }
  enrollments.value = []
  try {
    const res = await api.get('/enrollment/course', {
      params: { courseId: course.courseId }
    })
    enrollments.value = res.data
  } catch (err) {
    console.error('Failed to fetch course enrollments', err)
  }
}

// create a new course via API
const addCourse = async () => {
  try {
    await api.post('/course', newCourse.value)
    const res = await api.get('/course')
    courses.value = res.data
    newCourse.value = {
      name: '',
      code: '',
      credit: null,
      instructorId: null,
      instructorName: ''
    }
  } catch (err) {
    console.error('Failed to add course', err)
  }
}

// delete a course and refresh list
const removeCourse = async (id) => {
  try {
    await api.delete(`/course/${id}`)
    const res = await api.get('/course')
    courses.value = res.data
    if (selectedCourse.value && selectedCourse.value.courseId === id) {
      selectedCourse.value = null
      enrollments.value = []
    }
  } catch (err) {
    console.error('Failed to delete course', err)
  }
}

// persist changes made to the selected course
const updateCourse = async () => {
  try {
    await api.put(`/course/${selectedCourse.value.code}`, editCourse.value)
    const res = await api.get('/course')
    courses.value = res.data
    const found = courses.value.find(c => c.courseId === selectedCourse.value.courseId)
    if (found) {
      selectedCourse.value = found
      editCourse.value = { ...found }
    }
  } catch (err) {
    console.error('Failed to update course', err)
  }
}

// fetch courses on component mount
onMounted(async () => {
  try {
    const response = await api.get('/course')
    courses.value = response.data
  } catch (err) {
    console.error('Failed to fetch courses', err)
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
