<template>
  <!-- Root layout containing navigation and route outlet -->
  <div>
    <h1>Student Information System</h1>
    <nav class="nav">
      <RouterLink to="/">Students</RouterLink>
      <RouterLink to="/courses">Courses</RouterLink>
      <RouterLink to="/instructors">Instructors</RouterLink>
      <RouterLink to="/stats">Statistics</RouterLink>
    </nav>
    <RouterView />
  </div>
</template>

<script setup>
// Router components used for navigation
import { RouterLink, RouterView } from 'vue-router'
</script>

<style>
h1 {
  text-align: center;
  margin-bottom: 20px;
}

.nav {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-bottom: 20px;
  background: #333;
  padding: 0.5rem;
}

.nav a {
  color: white;
  text-decoration: none;
}

.nav a.router-link-active {
  text-decoration: underline;
}
</style>
