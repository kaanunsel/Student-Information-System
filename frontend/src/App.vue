<template>
  <div>
    <h1>Student Information System</h1>
    <nav>
        <router-link to="/students">Students</router-link>
        <router-link to="/courses">Courses</router-link>
        <router-link to="/instructors">Instructors</router-link>
        <router-link to="/enrollments">Enrollments</router-link>
        <router-link to="/analytics">Analytics</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<style scoped>
</style>
