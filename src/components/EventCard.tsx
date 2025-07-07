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
    try {
      const date = new Date(dateString);
      if (isNaN(date.getTime())) {
        return dateString; // Retorna a string original se não conseguir converter
      }
      return date.toLocaleDateString('pt-BR');
    } catch (error) {
      console.error('Erro ao formatar data:', error);
      return dateString;
    }
  };

  return (
      <div className="bg-slate-800/50 backdrop-blur-sm rounded-xl p-6 hover:bg-slate-700/50 transition-all duration-300 border border-slate-700/50 hover:border-orange-500/30 hover:shadow-lg hover:shadow-orange-500/10 group">
        <div className="flex justify-between items-start mb-4">
          <h3 className="text-xl font-semibold text-white group-hover:text-orange-300 transition-colors">{event.titulo}</h3>
          {isAdmin && (
              <div className="flex gap-2">
                <button
                    onClick={() => onEdit?.(event)}
                    className="p-2 text-blue-400 hover:text-blue-300 hover:bg-slate-600/50 rounded-lg transition-colors"
                >
                  <Edit size={18} />
                </button>
                <button
                    onClick={() => onDelete?.(event.id!)}
                    className="p-2 text-red-400 hover:text-red-300 hover:bg-slate-600/50 rounded-lg transition-colors"
                >
                  <Trash2 size={18} />
                </button>
              </div>
          )}
        </div>

        <p className="text-slate-300 mb-6 leading-relaxed line-clamp-3">{event.descricao}</p>

        <div className="space-y-3">
          <div className="flex items-center gap-3 text-slate-400 group-hover:text-slate-300 transition-colors">
            <Calendar size={16} />
            <span>{formatDate(event.data)}</span>
          </div>

          <div className="flex items-center gap-3 text-slate-400 group-hover:text-slate-300 transition-colors">
            <Clock size={16} />
            <span>{event.hora}</span>
          </div>

          <div className="flex items-center gap-3 text-slate-400 group-hover:text-slate-300 transition-colors">
            <MapPin size={16} />
            <span>{event.local}</span>
          </div>

          <div className="flex items-center gap-3 text-slate-400 group-hover:text-slate-300 transition-colors">
            <User size={16} />
            <span>{event.organizador}</span>
          </div>
        </div>
      </div>
  );
};