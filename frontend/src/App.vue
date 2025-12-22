<template>
    <div id="app">
        <Sidebar />
        
        <main class="main-content">
            <div class="content-wrapper">
                <router-view v-slot="{ Component }">
                    <Transition name="fade" mode="out-in">
                        <component :is="Component" />
                    </Transition>
                </router-view>
            </div>
        </main>
    </div>
</template>

<script setup>
import Sidebar from "./components/Sidebar.vue"
</script>

<style scoped>
#app {
  display: flex;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: var(--bg-body);
}

.main-content {
  flex: 1;
  overflow-y: auto;
  position: relative;
  /* Custom scrollbar for webkit */
}

.main-content::-webkit-scrollbar {
  width: 8px;
}
.main-content::-webkit-scrollbar-track {
  background: transparent;
}
.main-content::-webkit-scrollbar-thumb {
  background: var(--gray-300);
  border-radius: 4px;
}

.content-wrapper {
  padding: 2rem;
  max-width: 1400px;
  margin: 0 auto;
}
</style>
