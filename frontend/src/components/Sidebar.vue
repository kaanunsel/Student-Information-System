<template>
  <aside class="sidebar">
    <div class="logo-area">
      <div class="logo-icon">S</div>
      <h1 class="logo-text">SIS</h1>
    </div>

    <nav class="nav-menu">
      <router-link to="/students" class="nav-item">
        <UsersIcon size="20" />
        <span>Students</span>
      </router-link>
      <router-link to="/courses" class="nav-item">
        <BookOpenIcon size="20" />
        <span>Courses</span>
      </router-link>
      <router-link to="/instructors" class="nav-item">
        <GraduationCapIcon size="20" />
        <span>Instructors</span>
      </router-link>
      <router-link to="/enrollments" class="nav-item">
        <ClipboardListIcon size="20" />
        <span>Enrollments</span>
      </router-link>
      <router-link to="/analytics" class="nav-item">
        <BarChart2Icon size="20" />
        <span>Analytics</span>
      </router-link>
    </nav>

    <div class="sidebar-footer">
      <transition name="fade">
        <div v-if="isUserMenuOpen" class="user-menu">
          <div class="user-menu-item" @click="setUser('Admin')">
            <div class="avatar-sm">A</div>
            <span>Admin</span>
          </div>
          <div class="user-menu-item" @click="setUser('Instructor')">
            <div class="avatar-sm">I</div>
            <span>Instructor</span>
          </div>
          <div class="user-menu-item" @click="setUser('Student')">
            <div class="avatar-sm">S</div>
            <span>Student</span>
          </div>
        </div>
      </transition>

      <div class="user-profile" @click="toggleUserMenu">
        <div class="avatar">{{ currentUser.charAt(0) }}</div>
        <div class="user-info">
          <span class="name">{{ currentUser }} User</span>
          <span class="role">{{ currentRole }}</span>
        </div>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { ref } from 'vue'
import {
  UsersIcon,
  BookOpenIcon,
  GraduationCapIcon,
  ClipboardListIcon,
  BarChart2Icon
} from 'lucide-vue-next'

const isUserMenuOpen = ref(false)
const currentUser = ref('Admin')
const currentRole = ref('Administrator')

const toggleUserMenu = () => {
  isUserMenuOpen.value = !isUserMenuOpen.value
}

const setUser = (user) => {
  currentUser.value = user
  if (user === 'Admin') currentRole.value = 'Administrator'
  else if (user === 'Instructor') currentRole.value = 'Department Head'
  else currentRole.value = 'Undergraduate'

  isUserMenuOpen.value = false
}

</script>

<style scoped>
.sidebar {
  width: 260px;
  background: var(--bg-sidebar);
  border-right: 1px solid var(--border-color);
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding: 1.5rem;
  position: relative;
  z-index: 50;
}

.logo-area {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 3rem;
  padding: 0 0.5rem;
}

.logo-icon {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, var(--primary-600), var(--secondary-500));
  border-radius: 10px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 1.5rem;
  box-shadow: 0 4px 10px rgba(99, 102, 241, 0.3);
}

.logo-text {
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0;
  background: linear-gradient(to right, var(--text-main), var(--primary-600));
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.nav-menu {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  flex: 1;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.9rem 1rem;
  border-radius: var(--radius-md);
  color: var(--text-muted);
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
}

.nav-item:hover {
  background: var(--bg-body);
  color: var(--primary-600);
  transform: translateX(4px);
}

.nav-item.router-link-active {
  background: linear-gradient(90deg, var(--primary-50), transparent);
  color: var(--primary-600);
  border-left: 3px solid var(--primary-600);
}

/* Dark mode adjustment for active state */
@media (prefers-color-scheme: dark) {
  .nav-item.router-link-active {
    background: linear-gradient(90deg, rgba(79, 70, 229, 0.1), transparent);
  }
}

.sidebar-footer {
  margin-top: auto;
  padding-top: 1.5rem;
  border-top: 1px solid var(--border-color);
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: var(--radius-md);
  transition: background 0.2s;
}

.user-profile:hover {
  background: var(--gray-100);
}

.avatar-sm {
  width: 24px;
  height: 24px;
  background: var(--primary-100);
  color: var(--primary-700);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  font-weight: 600;
}

.avatar {
  width: 36px;
  height: 36px;
  background: var(--gray-200);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  color: var(--text-muted);
}

.user-info {
  display: flex;
  flex-direction: column;
}

.name {
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-main);
}

.role {
  font-size: 0.75rem;
  color: var(--text-muted);
}
</style>
