import axios from 'axios'

const apiClient = axios.create({
  baseURL: process.env.VUE_APP_API_URL || '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

// Si existe token guardado (login), lo usamos en cada request
const token = localStorage.getItem('token')
if (token) {
  apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

export default apiClient