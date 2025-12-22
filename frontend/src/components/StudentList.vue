<template>
    <div class="page-container">
        <div class="page-header flex justify-between items-center">
            <div>
                <h2 class="page-title">Students</h2>
                <p style="color: var(--text-muted); margin: 0;">Manage your student records</p>
            </div>
            <BaseButton @click="showAddModal = true">
                <template #icon-left><PlusIcon size="18"/></template>
                Add Student
            </BaseButton>
        </div>

        <BaseCard class="mb-8" :no-padding="false">
            <template #header>
                <div class="flex items-center gap-2">
                    <FilterIcon size="18" class="text-muted"/>
                    <span class="font-medium">Filters</span>
                </div>
            </template>
            
            <form @submit.prevent="applyFilter" class="filter-grid">
                <BaseInput v-model="filters.id" placeholder="Student ID" type="number">
                     <template #prefix><SearchIcon size="16" class="text-muted input-icon"/></template>
                </BaseInput>
                <BaseInput v-model="filters.name" placeholder="First Name" />
                <BaseInput v-model="filters.surname" placeholder="Last Name" />
                
                <div class="flex gap-2 items-end pb-4">
                    <BaseButton type="submit" variant="primary" size="sm">Apply</BaseButton>
                    <BaseButton type="button" variant="ghost" size="sm" @click="resetFilter">Reset</BaseButton>
                </div>
            </form>
        </BaseCard>

        <BaseCard :no-padding="true">
            <BaseTable :headers="['ID', 'Name', 'Email', 'Birth Date', 'Advisor', 'Actions']">
                <tr v-for="student in students" :key="student.studentId">
                    <td>#{{ student.studentId }}</td>
                    <td>
                        <div class="flex flex-col">
                            <span class="font-medium">{{ student.name }} {{ student.surname }}</span>
                        </div>
                    </td>
                    <td>{{ student.email }}</td>
                    <td>{{ student.birthDate }}</td>
                    <td>
                        <div v-if="student.advisorName" class="badge">
                            {{ student.advisorName }}
                        </div>
                        <span v-else class="text-muted">-</span>
                    </td>
                    <td>
                        <div class="flex gap-2">
                            <BaseButton variant="secondary" size="sm" @click="startEdit(student)">
                                <Edit2Icon size="14"/>
                            </BaseButton>
                            <BaseButton variant="danger" size="sm" @click="deleteStudent(student.studentId)">
                                <Trash2Icon size="14"/>
                            </BaseButton>
                        </div>
                    </td>
                </tr>
                 <tr v-if="students.length === 0">
                    <td colspan="6" class="text-center py-8 text-muted">
                        No students found.
                    </td>
                </tr>
            </BaseTable>
        </BaseCard>

        <!-- Edit Student Modal -->
        <BaseModal :isOpen="!!editingStudent" title="Edit Student" @close="cancelEdit">
            <form v-if="editingStudent" @submit.prevent="submitEdit" class="flex flex-col gap-4">
                <BaseInput id="edit-name" label="Name" v-model="editingStudent.name" required />
                <BaseInput id="edit-surname" label="Surname" v-model="editingStudent.surname" required />
                <BaseInput id="edit-email" label="Email" v-model="editingStudent.email" required type="email" />
                <BaseInput id="edit-birthDate" label="Birth Date" v-model="editingStudent.birthDate" type="date" required />
                <BaseInput id="edit-advisorId" label="Advisor ID" v-model="editingStudent.advisorId" type="number" required />
                
                <div class="flex justify-end gap-2 mt-4">
                    <BaseButton type="button" variant="ghost" @click="cancelEdit">Cancel</BaseButton>
                    <BaseButton type="submit" variant="primary">Save Changes</BaseButton>
                </div>
            </form>
        </BaseModal>

        <!-- Add Student Modal (Reusing existing component logic or refactoring logic here) -->
        <BaseModal :isOpen="showAddModal" title="Add New Student" @close="showAddModal = false">
             <AddStudentForm @student-added="onStudentAdded" @cancel="showAddModal = false" />
        </BaseModal>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import api from "../services/api"
import BaseButton from "./ui/BaseButton.vue"
import BaseInput from "./ui/BaseInput.vue"
import BaseCard from "./ui/BaseCard.vue"
import BaseTable from "./ui/BaseTable.vue"
import BaseModal from "./ui/BaseModal.vue"
import AddStudentForm from "./AddStudent.vue" // We will lightly refactor AddStudent to be specifically form content
import { PlusIcon, SearchIcon, FilterIcon, Edit2Icon, Trash2Icon } from 'lucide-vue-next'

// --- Reactive State ---
const students = ref([])
const editingStudent = ref(null)
const showAddModal = ref(false)
const filters = ref({
    id: null,
    name: "",
    surname: ""
})

// --- Core Logic ---
const refreshStudents = async () => {
    try {
        const res = await api.getStudents(filters.value)
        students.value = res.data
    } catch (e) {
        console.error("Error fetching students:", e)
    }
}

function startEdit(student) {
  editingStudent.value = { ...student }
}

function cancelEdit() {
    editingStudent.value = null
}

async function submitEdit() {
    try {
        const studentId = editingStudent.value.studentId;
        await api.updateStudent(studentId, editingStudent.value)
        await refreshStudents()
        editingStudent.value = null
    } catch (e) {
        console.error("Error updating student:", e)
        alert("Error updating student.")
    }
}

async function deleteStudent(studentId) {
    if (!confirm("Are you sure?")) return;
    try {
        await api.deleteStudent(studentId)
        await refreshStudents()
    }catch (e) {
        console.error("Error deleting student:", e)
    }
}

// --- Filter Handling ---
function applyFilter(){
    refreshStudents()
}

function resetFilter(){
    filters.value = { id: null, name: '', surname: '' }
    refreshStudents()
}

function onStudentAdded() {
    showAddModal.value = false;
    refreshStudents();
}

// --- Lifecycle Hooks ---
onMounted(refreshStudents)
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
.badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 999px;
    background: var(--primary-100);
    color: var(--primary-700);
    font-size: 0.75rem;
    font-weight: 600;
}
.text-muted {
    color: var(--text-muted);
}
.font-medium {
    font-weight: 500;
}
</style>