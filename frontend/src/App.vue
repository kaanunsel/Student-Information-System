<template>
  <div>
    <h1>Student Information System</h1>
    <nav>
        <router-link to="/students">Students</router-link>  |
        <router-link to="/courses">Courses</router-link>  |
        <router-link to="/instructors">Instructors</router-link> |
        <router-link to="/enrollments">Enrollments</router-link> |
        <router-link to="/analytics">Analytics</router-link>
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
