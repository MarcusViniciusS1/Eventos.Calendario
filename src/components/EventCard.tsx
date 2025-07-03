import React from 'react';
import { Calendar, Clock, MapPin, User, Edit, Trash2 } from 'lucide-react';
import { Event } from '../types/Event';

interface EventCardProps {
  event: Event;
  isAdmin?: boolean;
  onEdit?: (event: Event) => void;
  onDelete?: (id: number) => void;
}

export const EventCard: React.FC<EventCardProps> = ({ 
  event, 
  isAdmin = false, 
  onEdit, 
  onDelete 
}) => {
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
  };

  return (
    <div className="bg-slate-700 rounded-lg p-6 hover:bg-slate-600 transition-colors duration-200 border border-slate-600">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-xl font-semibold text-white">{event.titulo}</h3>
        {isAdmin && (
          <div className="flex gap-2">
            <button
              onClick={() => onEdit?.(event)}
              className="p-2 text-blue-400 hover:text-blue-300 hover:bg-slate-600 rounded-lg transition-colors"
            >
              <Edit size={18} />
            </button>
            <button
              onClick={() => onDelete?.(event.id!)}
              className="p-2 text-red-400 hover:text-red-300 hover:bg-slate-600 rounded-lg transition-colors"
            >
              <Trash2 size={18} />
            </button>
          </div>
        )}
      </div>
      
      <p className="text-slate-300 mb-4 leading-relaxed">{event.descricao}</p>
      
      <div className="space-y-2">
        <div className="flex items-center gap-3 text-slate-400">
          <Calendar size={16} />
          <span>{formatDate(event.data)}</span>
        </div>
        
        <div className="flex items-center gap-3 text-slate-400">
          <Clock size={16} />
          <span>{event.hora}</span>
        </div>
        
        <div className="flex items-center gap-3 text-slate-400">
          <MapPin size={16} />
          <span>{event.local}</span>
        </div>
        
        <div className="flex items-center gap-3 text-slate-400">
          <User size={16} />
          <span>{event.organizador}</span>
        </div>
      </div>
    </div>
  );
};