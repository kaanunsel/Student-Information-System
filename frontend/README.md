# SIS Frontend

This is a small Vue.js single page application that works with the backend in this repository. It lists and manages students, courses and instructors from the REST API.
A statistics page shows course performance data using the analytics service.

## Development Server

To start the development server (requires Node.js installed):

```bash
npm install
npm run dev
```

By default the app expects the backend to run on `http://localhost:8080`. Adjust the Axios base URL in the components if needed.
