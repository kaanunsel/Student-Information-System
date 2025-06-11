import { createApp } from 'vue'
// root component and router configuration
import App from './App.vue'
import router from './router'

// bootstrap the Vue application
createApp(App).use(router).mount('#app')
