<template>
  <div class="form-group" :class="{ 'has-error': error }">
    <label v-if="label" :for="id" class="form-label">{{ label }}</label>
    <div class="input-wrapper">
      <slot name="prefix"></slot>
      <input v-bind="$attrs" :id="id" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)"
        class="form-input" />
      <slot name="suffix"></slot>
    </div>
    <span v-if="error" class="form-error">{{ error }}</span>
  </div>
</template>

<script setup>
defineProps({
  modelValue: [String, Number],
  label: String,
  id: String,
  error: String
})

defineEmits(['update:modelValue'])
</script>

<style scoped>
.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  margin-bottom: 0.5rem;
  color: var(--text-muted);
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  background: var(--bg-input);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  font-family: inherit;
  font-size: 0.95rem;
  color: var(--text-main);
  transition: all 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: var(--primary-500);
  box-shadow: 0 0 0 3px var(--primary-100);
  background: white;
  /* clearer on focus */
  color: var(--gray-900);
}

/* Dark mode adjustment for focus bg ?? - handled by global var usually but override here if needed */

.has-error .form-input {
  border-color: var(--danger);
}

.form-error {
  font-size: 0.8rem;
  color: var(--danger);
  margin-top: 0.25rem;
  display: block;
}
</style>
