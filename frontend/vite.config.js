import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Configuração padrão do Vite
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/alunos': 'http://localhost:8080',
      '/cursos': 'http://localhost:8080'
    }
  }
})
