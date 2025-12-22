<template>
    <form @submit.prevent="addCourse" class="flex flex-col gap-4">
        <BaseInput id="new-name" label="Course Name" v-model="newCourse.name" required />
        <BaseInput id="new-code" label="Course Code" v-model="newCourse.code" required />
        <BaseInput id="new-credit" label="Credit" v-model="newCourse.credit" type="number" required />
        <BaseInput id="new-instructorId" label="Instructor ID" v-model="newCourse.instructorId" type="number"
            required />

        <div class="flex justify-end gap-2 mt-4">
            <BaseButton type="button" variant="ghost" @click="$emit('cancel')">Cancel</BaseButton>
            <BaseButton type="submit" variant="primary">Add Course</BaseButton>
        </div>
    </form>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api' // Use api service
import BaseInput from './ui/BaseInput.vue'
import BaseButton from './ui/BaseButton.vue'

const newCourse = ref({
    name: "",
    code: "",
    credit: null,
    instructorId: null
})

const emit = defineEmits(["course-added", "cancel"])

const addCourse = async () => {
    try {
        const res = await api.createCourse(newCourse.value)
        if (res.status === 201 || res.status === 200) { // 201 Created or 200 OK
            newCourse.value = {
                name: "",
                code: "",
                credit: null,
                instructorId: null
            }
            emit('course-added')
        } else {
            alert("Failed to add course.")
        }
    } catch (error) {
        console.error('Error adding course:', error)
        alert("Error adding course.")
    }
}
</script>