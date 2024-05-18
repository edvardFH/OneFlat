import path from "path"
import { defineConfig } from "vite"
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  base: "/",
  plugins: [react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  preview: {
   port: 4200,
   strictPort: true,
  },
  server: {
   port: 4200,
   strictPort: true,
   host: true,
   origin: "http://0.0.0.0:4200",
  },
 });