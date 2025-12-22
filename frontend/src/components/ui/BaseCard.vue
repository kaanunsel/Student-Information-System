<template>
  <div class="glass-panel" :class="{ 'p-6': !noPadding }">
    <div v-if="$slots.header || title" class="card-header mb-4 flex justify-between items-center">
      <h3 v-if="title" class="card-title">{{ title }}</h3>
      <slot name="header"></slot>
    </div>
    <slot></slot>
    <div v-if="$slots.footer" class="card-footer mt-4 pt-4 border-t border-[var(--border-color)]">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: String,
  noPadding: {
    type: Boolean,
    default: false
  }
})
</script>

<style scoped>
.card-title {
  font-size: 1.25rem;
  margin: 0;
  color: var(--text-main);
}
</style>
