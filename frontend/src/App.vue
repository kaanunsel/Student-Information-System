<script setup>
import StudentList from './components/StudentList.vue'
import AddStudent from './components/AddStudent.vue'
import CourseList from './components/CourseList.vue'
import { ref } from 'vue'

</script>

<template>
  <div>
    <h1>Student Information System</h1>
    <nav>
        <router-link to="/students">Student List</router-link>  |
        <router-link to="/courses">Course List</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
