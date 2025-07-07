import React, { useState, useEffect } from 'react';
import { Calendar, LogOut, Plus } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { EventCard } from '../components/EventCard';
import { EventModal } from '../components/EventModal';
import { SearchBar } from '../components/SearchBar';
import { Event } from '../types/Event';
import { eventService } from '../services/api';

export const AdminPanel: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingEvent, setEditingEvent] = useState<Event | null>(null);
  
  const { logout } = useAuth();

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      setLoading(true);
      const response = await eventService.getEventsAdmin();
      setEvents(response.data);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar eventos');
      console.error('Erro ao carregar eventos:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateEvent = async (eventData: Omit<Event, 'id'>) => {
    try {
      await eventService.createEvent(eventData);
      loadEvents();
    } catch (err) {
      console.error('Erro ao criar evento:', err);
    }
  };

  const handleUpdateEvent = async (eventData: Omit<Event, 'id'>) => {
    if (!editingEvent?.id) return;
    
    try {
      await eventService.updateEvent(editingEvent.id, eventData);
      loadEvents();
      setEditingEvent(null);
    } catch (err) {
      console.error('Erro ao atualizar evento:', err);
    }
  };

  const handleDeleteEvent = async (id: number) => {
    if (!confirm('Tem certeza que deseja excluir este evento?')) return;
    
    try {
      await eventService.deleteEvent(id);
      loadEvents();
    } catch (err) {
      console.error('Erro ao excluir evento:', err);
    }
  };

  const handleEditEvent = (event: Event) => {
    setEditingEvent(event);
    setIsModalOpen(true);
  };

  const handleSearch = async (titulo: string, organizador: string) => {
    try {
      setLoading(true);
      const response = await eventService.searchEvents(titulo || undefined, organizador || undefined);
      setEvents(response.data);
    } catch (err) {
      console.error('Erro ao buscar eventos:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleNewEvent = () => {
    setEditingEvent(null);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingEvent(null);
  };

  return (
    <div className="min-h-screen bg-slate-800">
      <header className="bg-slate-900 border-b border-slate-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center gap-3">
              <Calendar className="text-orange-400" size={24} />
              <h1 className="text-xl font-semibold text-white">Gerenciar Eventos</h1>
            </div>
            
            <button
              onClick={logout}
              className="flex items-center gap-2 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
            >
              <LogOut size={18} />
              Sair
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex justify-between items-center mb-8">
          <h2 className="text-2xl font-semibold text-white">Painel Administrativo</h2>
          <button
            onClick={handleNewEvent}
            className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
          >
            <Plus size={18} />
            Novo Evento
          </button>
        </div>

        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>

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
            <h3 className="text-xl font-semibold text-slate-400 mb-2">
              Nenhum evento encontrado
            </h3>
            <p className="text-slate-500 mb-6">
              Não há eventos cadastrados no momento.
            </p>
            <button
              onClick={handleNewEvent}
              className="flex items-center gap-2 mx-auto px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
            >
              <Plus size={18} />
              Criar Primeiro Evento
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {events.map((event) => (
              <EventCard
                key={event.id}
                event={event}
                isAdmin={true}
                onEdit={handleEditEvent}
                onDelete={handleDeleteEvent}
              />
            ))}
          </div>
        )}
      </main>

      <EventModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onSave={editingEvent ? handleUpdateEvent : handleCreateEvent}
        event={editingEvent}
      />
    </div>
  );
};