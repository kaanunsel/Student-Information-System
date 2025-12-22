<template>
    <div class="page-container">
        <div class="page-header flex justify-between items-center">
            <div>
                <h2 class="page-title">Courses</h2>
                <p style="color: var(--text-muted); margin: 0;">Manage course offerings</p>
            </div>
            <BaseButton @click="showAddModal = true">
                <template #icon-left>
                    <PlusIcon size="18" />
                </template>
                Add Course
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
                <BaseInput v-model="filters.id" placeholder="Course ID" type="number">
                    <template #prefix>
                        <SearchIcon size="16" class="text-muted input-icon" />
                    </template>
                </BaseInput>
                <BaseInput v-model="filters.name" placeholder="Course Name" />
                <BaseInput v-model="filters.code" placeholder="Course Code" />
                <BaseInput v-model="filters.instructorId" placeholder="Instructor ID" type="number" />

                <div class="flex gap-2 items-end pb-4">
                    <BaseButton type="submit" variant="primary" size="sm">Apply</BaseButton>
                    <BaseButton type="button" variant="ghost" size="sm" @click="resetFilter">Reset</BaseButton>
                </div>
            </form>
        </BaseCard>

        <BaseCard :no-padding="true">
            <BaseTable :headers="['ID', 'Name', 'Code', 'Credit', 'Instructor', 'Actions']">
                <tr v-for="course in courses" :key="course.courseId">
                    <td>#{{ course.courseId }}</td>
                    <td class="font-medium">{{ course.courseName }}</td>
                    <td>
                        <span class="badge-code">{{ course.courseCode }}</span>
                    </td>
                    <td>{{ course.credit }}</td>
                    <td>
                        <span v-if="course.instructorName">{{ course.instructorName }}</span>
                        <span v-else class="text-muted">-</span>
                    </td>
                    <td>
                        <div class="flex gap-2">
                            <BaseButton variant="secondary" size="sm" @click="startEdit(course)">
                                <Edit2Icon size="14" />
                            </BaseButton>
                            <BaseButton variant="danger" size="sm" @click="deleteCourse(course.courseId)">
                                <Trash2Icon size="14" />
                            </BaseButton>
                        </div>
                    </td>
                </tr>
                <tr v-if="courses.length === 0">
                    <td colspan="6" class="text-center py-8 text-muted">
                        No courses found.
                    </td>
                </tr>
            </BaseTable>
        </BaseCard>

        <!-- Add Course Modal -->
        <BaseModal :isOpen="showAddModal" title="Add New Course" @close="showAddModal = false">
            <AddCourseForm @course-added="onCourseAdded" @cancel="showAddModal = false" />
        </BaseModal>

        <!-- Edit Course Modal -->
        <BaseModal :isOpen="!!editingCourse" title="Edit Course" @close="cancelEdit">
            <form v-if="editingCourse" @submit.prevent="submitEdit" class="flex flex-col gap-4">
                <BaseInput id="edit-courseName" label="Course Name" v-model="editingCourse.courseName" required />
                <BaseInput id="edit-courseCode" label="Course Code" v-model="editingCourse.courseCode" required />
                <BaseInput id="edit-credit" label="Credit" v-model="editingCourse.credit" type="number" required />
                <BaseInput id="edit-instructorId" label="Instructor ID" v-model="editingCourse.instructorId"
                    type="number" required />

                <div class="flex justify-end gap-2 mt-4">
                    <BaseButton type="button" variant="ghost" @click="cancelEdit">Cancel</BaseButton>
                    <BaseButton type="submit" variant="primary">Save Changes</BaseButton>
                </div>
            </form>
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
import AddCourseForm from "./AddCourse.vue" // Will refactor this next
import { PlusIcon, SearchIcon, FilterIcon, Edit2Icon, Trash2Icon } from 'lucide-vue-next'

const courses = ref([])
const editingCourse = ref(null)
const showAddModal = ref(false)
const filters = ref({
    id: null,
    name: "",
    code: "",
    instructorId: null
})

const refreshCourses = async () => {
    try {
        const res = await api.getCourses(filters.value)
        courses.value = res.data
    } catch (e) {
        console.error("Error fetching courses:", e)
    }
}

function startEdit(course) {
    editingCourse.value = { ...course }
}

function cancelEdit() {
    editingCourse.value = null
}

async function submitEdit() {
    try {
        await api.updateCourse(editingCourse.value.courseId, editingCourse.value)
        await refreshCourses()
        editingCourse.value = null
    } catch (e) {
        console.error("Error updating course:", e)
        alert('Error updating course')
    }
}

async function deleteCourse(courseId) {
    if (!confirm('Are you sure?')) return
    try {
        await api.deleteCourse(courseId)
        await refreshCourses()
    } catch (e) {
        console.error('Error deleting course:', e)
    }
}

function applyFilter() {
    refreshCourses()
}

function resetFilter() {
    filters.value = { id: null, name: '', code: '', instructorId: null }
    refreshCourses()
}

function onCourseAdded() {
    showAddModal.value = false
    refreshCourses()
}

onMounted(refreshCourses)
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

.badge-code {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 4px;
    background: var(--gray-200);
    color: var(--gray-800);
    font-family: monospace;
    font-weight: 600;
    font-size: 0.85rem;
}

.text-muted {
    color: var(--text-muted);
}

.font-medium {
    font-weight: 500;
}
</style>