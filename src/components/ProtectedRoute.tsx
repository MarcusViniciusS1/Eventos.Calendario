import React from 'react';
<<<<<<< HEAD
import { Event } from '../types/Event';
=======
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
<<<<<<< HEAD
  // Sempre permitir acesso - sem proteção
  return <>{children}</>;
=======
  const { isAuthenticated } = useAuth();
  
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />;
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
};