import axios from 'axios';
import { Event, LoginRequest, LoginResponse } from '../types/Event';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
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
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const eventService = {
  // Endpoints públicos
  getAllEvents: () => api.get<Event[]>('/eventos/publico'),
  
  // Endpoints protegidos
  login: (credentials: LoginRequest) => 
    api.post<LoginResponse>('/auth/login', credentials),
  
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