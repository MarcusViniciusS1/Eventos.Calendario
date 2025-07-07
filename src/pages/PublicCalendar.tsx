import React, { useState, useEffect } from 'react';
import { Calendar, LogIn } from 'lucide-react';
import { Link } from 'react-router-dom';
import { EventCard } from '../components/EventCard';
import { Event } from '../types/Event';
<<<<<<< HEAD
import { eventService } from '../services/api';
=======
import { eventService } from '../services/api'
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1

export const PublicCalendar: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      setLoading(true);
      const response = await eventService.getAllEvents();
      setEvents(response.data);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar eventos');
      console.error('Erro ao carregar eventos:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-800">
      <header className="bg-slate-900 border-b border-slate-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center gap-3">
              <Calendar className="text-orange-400" size={24} />
              <h1 className="text-xl font-semibold text-white">Calendário de Eventos</h1>
            </div>
            
            <Link
              to="/login"
              className="flex items-center gap-2 px-4 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition-colors"
            >
              <LogIn size={18} />
              ENTRAR
            </Link>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-400"></div>
          </div>
        ) : error ? (
          <div className="text-center py-12">
            <p className="text-red-400">{error}</p>
          </div>
        ) : events.length === 0 ? (
          <div className="text-center py-12">
            <Calendar className="mx-auto text-slate-500 mb-4" size={64} />
            <h2 className="text-xl font-semibold text-slate-400 mb-2">
              Nenhum evento encontrado
            </h2>
            <p className="text-slate-500">
<<<<<<< HEAD
              Não há eventos cadastrados até o momento.
=======
              Não há eventos cadastrados até o momento!
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
            </p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {events.map((event) => (
              <EventCard key={event.id} event={event} />
            ))}
          </div>
        )}
      </main>
    </div>
  );
};