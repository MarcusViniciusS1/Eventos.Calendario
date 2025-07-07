import axios from 'axios';
import { Event, LoginRequest, LoginResponse } from '../types/Event';

const API_BASE_URL = 'http://localhost:8080/api';

console.log('🌐 API Base URL:', API_BASE_URL);

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 segundos de timeout
});

// Interceptor para adicionar token JWT
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor para tratar erros de autenticação
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      // Só redirecionar se não for um endpoint público
      if (!error.config?.url?.includes('/publico') && !error.config?.url?.includes('/login')) {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export const eventService = {
  // Endpoints públicos
  getAllEvents: () => api.get<Event[]>('/eventos/publico'),
  
  // Endpoints de autenticação
  login: (credentials: LoginRequest) => 
    api.post<LoginResponse>('/auth/login', credentials),
  
  register: (userData: { nome: string; email: string; senha: string }) =>
    api.post('/auth/register', userData),
  
  validateToken: () => 
    api.post('/auth/validate', {}, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    }),
  
  // Endpoints protegidos
  getEventsAdmin: () => api.get<Event[]>('/eventos'),
  
  createEvent: (event: Omit<Event, 'id'>) => 
    api.post<Event>('/eventos', event),
  
  updateEvent: (id: number, event: Omit<Event, 'id'>) => 
    api.put<Event>(`/eventos/${id}`, event),
  
  deleteEvent: (id: number) => 
    api.delete(`/eventos/${id}`),
  
  searchEvents: (titulo?: string, organizador?: string) => 
    api.get<Event[]>('/eventos/buscar', {
      params: { titulo, organizador }
    }),
};

export default api;