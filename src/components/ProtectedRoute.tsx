import React from 'react';
import { Event } from '../types/Event';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
  // Sempre permitir acesso - sem proteção
  return <>{children}</>;
};