import axios from 'axios';
import { Event, LoginRequest, LoginResponse } from '../types/Event';

<<<<<<< HEAD
const API_BASE_URL = 'http://localhost:8080/eventos';
=======
const API_BASE_URL = 'http://localhost:8080/api';
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

<<<<<<< HEAD
// Interceptor para adicionar token JWT
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

<<<<<<< HEAD
// Interceptor para tratar erros de autenticação
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
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
<<<<<<< HEAD
  // Endpoints públicos
  getAllEvents: () => api.get<Event[]>('/eventos/publico'),
  
  // Endpoints protegidos
=======

  getAllEvents: () => api.get<Event[]>('/eventos/publico'),
  

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
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