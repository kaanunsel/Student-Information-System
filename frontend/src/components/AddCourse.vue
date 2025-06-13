<template>
    <div>
        <h2>Add New Course</h2>
        <form @submit.prevent="addCourse">
            <div>
                <label for="name">Name:</label>
                <input id="name" v-model="newCourse.name" required />
            </div>
            <div>
                <label for="code">Code:</label>
                <input id="code" v-model="newCourse.code" required />
            </div>
            <div>
                <label for="credit">Credit:</label>
                <input id="credit" v-model="newCourse.credit" type="number" required />
            </div>
            <div>
                <label for="instructorId">Instructor ID:</label>
                <input id="instructorId" v-model="newCourse.instructorId" type="number" required />
            </div>
            <button type="submit">Add Course</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from "vue"

const newCourse = ref({
    name: "",
    code: "",
    credit: null,
    instructorId: null
})

async function addCourse(){
    try{
        const res = await fetch('http://localhost:8080/course', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newCourse.value)
        })
        if(res.ok){
            newCourse.value = {
                name: "",
                code: "",
                credit: null,
                instructorId: null
            }
            alert('Course added successfully!')
            emit('course-added')
        } else {
            alert("Failed to add course.")
        }
    } catch(error) {
        console.error('Error adding course:', error)
        alert("Error adding course.")
    } 
}

const emit = defineEmits(["course-added"])
</script>