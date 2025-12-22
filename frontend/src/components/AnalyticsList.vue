<template>
  <div class="page-container glass-panel p-8" style="min-height: 80vh;">
    <div class="page-header mb-8">
      <h2 class="page-title">Analytics Dashboard</h2>
    </div>

    <!-- Charts Section -->
    <div class="grid grid-cols-2 gap-8 mb-8">
      <BaseCard title="Average Grades by Course">
        <div style="height: 300px; position: relative;">
          <Bar v-if="chartData.labels.length" :data="chartData" :options="chartOptions" />
          <div v-else class="flex items-center justify-center h-full text-muted">
            {{ stats.length ? 'Loading chart data...' : 'No data available' }}
          </div>
        </div>
      </BaseCard>

      <BaseCard title="Student Enrollment Distribution">
        <div style="height: 300px; position: relative;">
          <Bar v-if="enrollmentChartData.labels.length" :data="enrollmentChartData" :options="enrollmentChartOptions" />
          <div v-else class="flex items-center justify-center h-full text-muted">
            {{ stats.length ? 'Loading chart data...' : 'No data available' }}
          </div>
        </div>
      </BaseCard>
    </div>

    <BaseCard title="Detailed Performance Data" :no-padding="true">
      <BaseTable :headers="['Course ID', 'Course Name', 'Students', 'Avg Grade', 'Min/Max']">
        <tr v-for="stat in stats" :key="stat.courseId">
          <td>#{{ stat.courseId }}</td>
          <td class="font-medium">{{ stat.courseName }}</td>
          <td>
            <div class="flex items-center gap-2">
              <UsersIcon size="16" class="text-muted" />
              {{ stat.numberOfStudents }}
            </div>
          </td>
          <td>
            <span :class="getGradeColorClass(stat.avgGrade)" class="font-bold">
              {{ stat.avgGrade ? stat.avgGrade.toFixed(2) : 'N/A' }}
            </span>
          </td>
          <td>
            <div class="text-sm">
              <span class="text-danger">{{ stat.minGrade || 0 }}</span>
              <span class="text-muted mx-1">/</span>
              <span class="text-success">{{ stat.maxGrade || 0 }}</span>
            </div>
          </td>
        </tr>
      </BaseTable>
    </BaseCard>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../services/api'
import BaseCard from './ui/BaseCard.vue'
import BaseTable from './ui/BaseTable.vue'
import { UsersIcon } from 'lucide-vue-next'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'
import { Bar } from 'vue-chartjs'

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

const stats = ref([])

const fetchStats = async () => {
  try {
    const res = await api.getCoursePerformance()
    stats.value = res.data
  } catch (error) {
    console.error('Error fetching analytics:', error)
  }
}

// Chart 1: Average Grades
const chartData = computed(() => {
  return {
    labels: stats.value.map(s => s.courseName),
    datasets: [{
      label: 'Average Grade',
      backgroundColor: '#6366f1',
      borderRadius: 6,
      data: stats.value.map(s => s.avgGrade || 0)
    }]
  }
})

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false }
  },
  scales: {
    y: {
      beginAtZero: true,
      max: 100,
      grid: { color: 'rgba(0,0,0,0.05)' }
    },
    x: { grid: { display: false } }
  }
}

// Chart 2: Enrollment Numbers
const enrollmentChartData = computed(() => {
  return {
    labels: stats.value.map(s => s.courseName),
    datasets: [{
      label: 'Students Enrolled',
      backgroundColor: '#a855f7', // Secondary color
      borderRadius: 6,
      data: stats.value.map(s => s.numberOfStudents)
    }]
  }
})

const enrollmentChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false }
  },
  scales: {
    y: {
      beginAtZero: true,
      ticks: { precision: 0 },
      grid: { color: 'rgba(0,0,0,0.05)' }
    },
    x: { grid: { display: false } }
  }
}

function getGradeColorClass(grade) {
  if (!grade) return 'text-muted'
  if (grade >= 90) return 'text-success'
  if (grade >= 70) return 'text-primary'
  if (grade >= 50) return 'text-warning'
  return 'text-danger'
}

onMounted(fetchStats)
</script>

<style scoped>
.grid {
  display: grid;
}

.grid-cols-2 {
  grid-template-columns: repeat(2, 1fr);
}

.text-success {
  color: var(--success);
}

.text-danger {
  color: var(--danger);
}

.text-warning {
  color: var(--warning);
}

.text-primary {
  color: var(--primary-600);
}

.font-bold {
  font-weight: 700;
}

@media (max-width: 1024px) {
  .grid-cols-2 {
    grid-template-columns: 1fr;
  }
}
</style>