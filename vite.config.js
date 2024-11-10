import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: '../../backend/IoT119/src/main/resources/static'
  },
  server: {
    hmr: {
      overlay: false, // Disable error overlay
    },
  },
  optimizeDeps: {
    include: ['chart.js', 'vue-chartjs'],
  },
})