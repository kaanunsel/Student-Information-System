<template>
  <button 
    :class="['btn', `btn-${variant}`, size === 'sm' ? 'btn-sm' : '', block ? 'w-full' : '']"
    @click="$emit('click', $event)"
  >
    <slot name="icon-left"></slot>
    <slot></slot>
    <slot name="icon-right"></slot>
  </button>
</template>

<script setup>
defineProps({
  variant: {
    type: String,
    default: 'primary', // primary, secondary, danger, ghost
    validator: (value) => ['primary', 'secondary', 'danger', 'ghost'].includes(value)
  },
  size: {
    type: String,
    default: 'md' // sm, md
  },
  block: {
    type: Boolean,
    default: false
  }
})

defineEmits(['click'])
</script>

<style scoped>
.btn-primary {
  background: var(--primary-600);
  color: white;
  box-shadow: 0 4px 14px 0 rgba(79, 70, 229, 0.4);
}
.btn-primary:hover {
  background: var(--primary-500);
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.25);
  transform: translateY(-1px);
}

.btn-secondary {
  background: var(--bg-glass);
  color: var(--text-main);
  border: 1px solid var(--border-color);
}
.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.6);
  border-color: var(--primary-200);
}

.btn-danger {
  background: rgba(239, 68, 68, 0.1);
  color: var(--danger);
  border: 1px solid transparent;
}
.btn-danger:hover {
  background: rgba(239, 68, 68, 0.2);
  border-color: rgba(239, 68, 68, 0.3);
}

.btn-ghost {
  background: transparent;
  color: var(--text-muted);
}
.btn-ghost:hover {
  background: rgba(0,0,0,0.05);
  color: var(--text-main);
}

.btn-sm {
  padding: 0.4rem 0.8rem;
  font-size: 0.85rem;
}
</style>
