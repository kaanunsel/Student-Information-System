import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router"

// Create the Vue application instance, register the router, and mount it to the DOM.
createApp(App).use(router).mount('#app')
