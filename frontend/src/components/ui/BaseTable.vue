<template>
  <div class="table-container">
    <table>
      <thead>
        <tr>
          <th v-for="header in headers" :key="header">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <slot></slot>
      </tbody>
    </table>
  </div>
</template>

<script setup>
defineProps({
  headers: {
    type: Array,
    required: true
  }
})
</script>

<style scoped>
.table-container {
  overflow-x: auto;
  border-radius: var(--radius-lg);
  border: 1px solid var(--border-color);
  background: rgba(255, 255, 255, 0.4);
}

table {
  width: 100%;
  border-collapse: collapse;
  white-space: nowrap;
}

th {
  background: rgba(255, 255, 255, 0.5);
  text-align: left;
  padding: 1rem 1.5rem;
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: var(--text-muted);
  color: var(--text-muted);
  border-bottom: 1px solid var(--border-color);
  font-weight: 600;
}

td {
  padding: 1rem 1.5rem;
  border-bottom: 1px solid var(--border-color);
  color: var(--text-main);
}

tr:last-child td {
  border-bottom: none;
}

tbody tr {
  transition: background 0.2s;
}

tbody tr:hover {
  background: rgba(99, 102, 241, 0.05);
}

/* Dark mode th */
@media (prefers-color-scheme: dark) {
  th {
    background: rgba(30, 41, 59, 0.5);
  }
}
</style>
