<template>
    <div class="page-container">
        <div class="page-header flex justify-between items-center">
            <div>
                <h2 class="page-title">Instructors</h2>
                <p style="color: var(--text-muted); margin: 0;">Manage teaching staff</p>
            </div>
            <BaseButton @click="showAddModal = true">
                <template #icon-left>
                    <PlusIcon size="18" />
                </template>
                Add Instructor
            </BaseButton>
        </div>

        <BaseCard class="mb-8" :no-padding="false">
            <template #header>
                <div class="flex items-center gap-2">
                    <FilterIcon size="18" class="text-muted" />
                    <span class="font-medium">Filters</span>
                </div>
            </template>
            <!-- Mock filter for consistency, even if API doesn't fully support it yet or if it does default -->
            <div class="text-muted text-sm italic">
                No filters available for instructors yet.
            </div>
        </BaseCard>

        <BaseCard :no-padding="true">
            <BaseTable :headers="['ID', 'Name', 'Email', 'Department', 'Hiring Date', 'Actions']">
                <tr v-for="instructor in instructors" :key="instructor.id">
                    <td>#{{ instructor.id }}</td>
                    <td class="font-medium">{{ instructor.name }} {{ instructor.surname }}</td>
                    <td>{{ instructor.email }}</td>
                    <td><span class="badge">{{ instructor.department }}</span></td>
                    <td>{{ instructor.hiringDate }}</td>
                    <td>
                        <div class="flex gap-2">
                            <BaseButton variant="secondary" size="sm" @click="startEdit(instructor)">
                                <Edit2Icon size="14" />
                            </BaseButton>
                            <BaseButton variant="danger" size="sm" @click="deleteInstructor(instructor.id)">
                                <Trash2Icon size="14" />
                            </BaseButton>
                        </div>
                    </td>
                </tr>
                <tr v-if="instructors.length === 0">
                    <td colspan="6" class="text-center py-8 text-muted">
                        No instructors found.
                    </td>
                </tr>
            </BaseTable>
        </BaseCard>

        <!-- Add Instructor Modal -->
        <BaseModal :isOpen="showAddModal" title="Add New Instructor" @close="showAddModal = false">
            <AddInstructorForm @instructor-added="onInstructorAdded" @cancel="showAddModal = false" />
        </BaseModal>

        <!-- Edit Instructor Modal -->
        <BaseModal :isOpen="!!editingInstructor" title="Edit Instructor" @close="cancelEdit">
            <form v-if="editingInstructor" @submit.prevent="submitEdit" class="flex flex-col gap-4">
                <BaseInput id="edit-name" label="Name" v-model="editingInstructor.name" required />
                <BaseInput id="edit-surname" label="Surname" v-model="editingInstructor.surname" required />
                <BaseInput id="edit-email" label="Email" v-model="editingInstructor.email" required />
                <BaseInput id="edit-dept" label="Department" v-model="editingInstructor.department" required />
                <BaseInput id="edit-hdate" label="Hiring Date" v-model="editingInstructor.hiringDate" type="date"
                    required />

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
import AddInstructorForm from "./AddInstructor.vue"
import { PlusIcon, Edit2Icon, Trash2Icon, FilterIcon } from 'lucide-vue-next'

const instructors = ref([])
const editingInstructor = ref(null)
const showAddModal = ref(false)

const refreshInstructors = async () => {
    try {
        const res = await api.getInstructors()
        instructors.value = res.data
    } catch (e) {
        console.error("Error fetching instructors:", e)
    }
}

function startEdit(instructor) {
    editingInstructor.value = { ...instructor }
}

function cancelEdit() {
    editingInstructor.value = null
}

async function submitEdit() {
    try {
        await api.updateInstructor(editingInstructor.value.id, editingInstructor.value)
        await refreshInstructors()
        editingInstructor.value = null
    } catch (e) {
        console.error('Error updating instructor:', e)
        alert('Error updating instructor.')
    }
}

async function deleteInstructor(id) {
    if (!confirm('Are you sure?')) return
    try {
        await api.deleteInstructor(id)
        await refreshInstructors()
    } catch (e) {
        console.error('Error deleting instructor:', e)
    }
}

function onInstructorAdded() {
    showAddModal.value = false
    refreshInstructors()
}

onMounted(refreshInstructors)
</script>

<style scoped>
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