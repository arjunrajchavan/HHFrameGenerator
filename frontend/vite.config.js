import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// During `npm run dev`, requests to /api and /b are proxied to your
// local Spring Boot server so you don't need CORS config for local dev.
//
// For production, `npm run build` outputs to ./dist by default.
// If you want Spring Boot to serve the built frontend as one deployable
// unit (recommended — see the README notes), change outDir below to
// point at your Spring Boot project's static resources folder, e.g.
// outDir: '../HHframeGenerator/src/main/resources/static'
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: 'dist',
    emptyOutDir: true
  },
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/b': 'http://localhost:8080'
    }
  }
})
