import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        extensions: ['.vue', '.css', '.js', '.json']
    },
    server: {
        proxy: {
            '^/api': {
                target: 'http://localhost:7010',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            }
        }
    }
})
