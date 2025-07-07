import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { eventService } from '../services/api';

interface User {
  id: number;
  email: string;
  nome: string;
  role: string;
}

interface AuthContextType {
  isAuthenticated: boolean;
  user: User | null;
  login: (email: string, password: string) => Promise<boolean>;
  logout: () => void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        console.log('Nenhum token encontrado');
        setLoading(false);
        return;
      }

      console.log('Validando token...');
      const response = await eventService.validateToken();
      
      if (response.data.valid) {
        console.log('Token válido, usuário autenticado:', response.data);
        setIsAuthenticated(true);
        setUser({
          id: response.data.userId,
          email: response.data.username,
          nome: response.data.nome,
          role: response.data.role
        });
      } else {
        console.log('Token inválido:', response.data.message);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
      }
    } catch (error) {
      console.error('Erro ao verificar autenticação:', error);
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    } finally {
      setLoading(false);
    }
  };

  const login = async (email: string, password: string): Promise<boolean> => {
    try {
      console.log('Iniciando login para:', email);
      
      const response = await eventService.login({ username: email, password });
      console.log('Resposta do login:', response.data);
      
      if (response.data.token) {
        console.log('Token recebido, salvando dados...');
        
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify({
          id: response.data.userId,
          email: response.data.username,
          nome: response.data.nome,
          role: response.data.role
        }));
        
        setIsAuthenticated(true);
        setUser({
          id: response.data.userId,
          email: response.data.username,
          nome: response.data.nome,
          role: response.data.role
        });
        
        console.log('Login bem-sucedido!');
        return true;
      }
      
      console.log('Login falhou: token não recebido');
      return false;
    } catch (error: any) {
      console.error('Erro no login:', error);
      
      if (error.response?.data?.message) {
        console.error('Mensagem de erro:', error.response.data.message);
      }
      
      return false;
    }
  };

  const logout = () => {
    console.log('Fazendo logout...');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setIsAuthenticated(false);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ 
      isAuthenticated, 
      user, 
      login, 
      logout, 
      loading 
    }}>
      {children}
    </AuthContext.Provider>
  );
};