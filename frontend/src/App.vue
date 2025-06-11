<template>
  <div>
    <h1>Student Information System</h1>
    <StudentList />
    <CourseList />
  </div>
</template>

<script setup>
import StudentList from './components/StudentList.vue'
import CourseList from './components/CourseList.vue'
</script>

<style>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
</style>
