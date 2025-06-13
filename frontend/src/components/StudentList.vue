<template>
    <div>
        <h2>Student List</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Birth Date</th>
                    <th>Advisor ID</th>
                    <th>Advisor Name</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="student in students" :key="student.studentId">
                    <td>{{ student.studentId }}</td>
                    <td>{{ student.name }}</td>
                    <td>{{ student.surname }}</td>
                    <td>{{ student.email }}</td>
                    <td>{{ student.birthDate }}</td>
                    <td>{{ student.advisorId }}</td>
                    <td>{{ student.advisorName }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
import { ref, onMounted} from "vue"

const students = ref([])

onMounted(async () => {
    const res = await fetch('http://localhost:8080/student')
    students.value = await res.json()
})

</script>