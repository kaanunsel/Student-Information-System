<template>
  <div class="page-container">
    <div class="page-header flex justify-between items-center">
      <div>
        <h2 class="page-title">Enrollments</h2>
        <p style="color: var(--text-muted); margin: 0;">Manage student course enrollments</p>
      </div>
      <BaseButton @click="showAddModal = true">
        <template #icon-left>
          <PlusIcon size="18" />
        </template>
        New Enrollment
      </BaseButton>
    </div>

    <BaseCard class="mb-8" :no-padding="false">
      <template #header>
        <div class="flex items-center gap-2">
          <FilterIcon size="18" class="text-muted" />
          <span class="font-medium">Filters</span>
        </div>
      </template>

      <form @submit.prevent="applyFilter" class="filter-grid">
        <BaseInput v-model="filters.studentId" placeholder="Student ID" type="number">
          <template #prefix>
            <SearchIcon size="16" class="text-muted input-icon" />
          </template>
        </BaseInput>
        <BaseInput v-model="filters.courseId" placeholder="Course ID" type="number" />

        <div class="flex gap-2 items-end pb-4">
          <BaseButton type="submit" variant="primary" size="sm">Apply</BaseButton>
          <BaseButton type="button" variant="ghost" size="sm" @click="resetFilter">Reset</BaseButton>
        </div>
      </form>
    </BaseCard>

    <BaseCard :no-padding="true">
      <BaseTable :headers="['Student', 'Course', 'Instructor', 'Grade', 'Actions']">
        <tr v-for="enrollment in enrollments" :key="enrollment.id">
          <td>
            <div class="flex flex-col">
              <span class="font-medium">{{ enrollment.studentName }} {{ enrollment.studentSurname }}</span>
              <span class="text-xs text-muted">ID: {{ enrollment.studentId }}</span>
            </div>
          </td>
          <td>
            <div class="flex flex-col">
              <span class="font-medium">{{ enrollment.courseName }}</span>
              <span class="text-xs text-muted">ID: {{ enrollment.courseId }}</span>
            </div>
          </td>
          <td>{{ enrollment.instructorName }} {{ enrollment.instructorSurname }}</td>
          <td>
            <input type="number" class="grade-input" v-model="enrollment.grade" placeholder="N/A" min="0" max="100"
              @change="updateGrade(enrollment)">
          </td>
          <td>
            <BaseButton variant="danger" size="sm" @click="deleteEnrollment(enrollment.id)">
              <Trash2Icon size="14" />
            </BaseButton>
          </td>
        </tr>
        <tr v-if="enrollments.length === 0">
          <td colspan="5" class="text-center py-8 text-muted">
            No enrollments found.
          </td>
        </tr>
      </BaseTable>
    </BaseCard>

    <!-- Add Enrollment Modal -->
    <BaseModal :isOpen="showAddModal" title="New Enrollment" @close="showAddModal = false">
      <form @submit.prevent="addEnrollment" class="flex flex-col gap-4">
        <BaseInput id="new-sid" label="Student ID" v-model="newEnrollment.studentId" type="number" required />
        <BaseInput id="new-cid" label="Course ID" v-model="newEnrollment.courseId" type="number" required />
        <BaseInput id="new-grade" label="Initial Grade (Optional)" v-model="newEnrollment.grade" type="number" min="0"
          max="100" />

        <div class="flex justify-end gap-2 mt-4">
          <BaseButton type="button" variant="ghost" @click="showAddModal = false">Cancel</BaseButton>
          <BaseButton type="submit" variant="primary">Enroll</BaseButton>
        </div>
      </form>
    </BaseModal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api'
import BaseButton from "./ui/BaseButton.vue"
import BaseInput from "./ui/BaseInput.vue"
import BaseCard from "./ui/BaseCard.vue"
import BaseTable from "./ui/BaseTable.vue"
import BaseModal from "./ui/BaseModal.vue"
import { PlusIcon, SearchIcon, FilterIcon, Trash2Icon } from 'lucide-vue-next'

const enrollments = ref([])
const showAddModal = ref(false)
const filters = ref({
  studentId: '',
  courseId: ''
})
const newEnrollment = ref({
  studentId: '',
  courseId: '',
  grade: null
})

const fetchEnrollments = async () => {
  try {
    const res = await api.getEnrollments(filters.value)
    enrollments.value = res.data
  } catch (error) {
    console.error('Error fetching enrollments:', error)
  }
}

async function addEnrollment() {
  try {
    const res = await api.createEnrollment(newEnrollment.value)
    if (res.status === 201) {
      await fetchEnrollments()
      newEnrollment.value = { studentId: '', courseId: '', grade: null }
      showAddModal.value = false
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
  if (!confirm("Are you sure?")) return;
  try {
    const res = await api.deleteEnrollment(enrollmentId)
    if (res.status === 200) {
      await fetchEnrollments()
    }
  } catch (e) {
    console.error('Error deleting enrollment:', e)
  }
}

async function updateGrade(enrollment) {
  try {
    const res = await api.updateEnrollmentGrade(enrollment.id, enrollment.grade)
    if (res.status === 200) {
      // alert('Grade updated!')
    }
  } catch (e) {
    console.error('Error updating grade:', e)
    alert('Error updating grade')
  }
}

function applyFilter() {
  fetchEnrollments()
}

function resetFilter() {
  filters.value = { studentId: "", courseId: "" }
  fetchEnrollments()
}

onMounted(fetchEnrollments)
</script>

<style scoped>
.filter-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  align-items: end;
}

.input-icon {
  margin-right: 0.5rem;
}

.text-muted {
  color: var(--text-muted);
}

.font-medium {
  font-weight: 500;
}

.text-xs {
  font-size: 0.75rem;
}

.grade-input {
  width: 60px;
  padding: 0.25rem 0.5rem;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  background: var(--bg-input);
  color: var(--text-main);
  text-align: center;
}

.grade-input:focus {
  outline: none;
  border-color: var(--primary-500);
}
</style>
