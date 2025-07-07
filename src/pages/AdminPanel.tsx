import React, { useState, useEffect } from 'react';
import { Calendar, LogOut, Plus, User, Shield, Home, BarChart3, Clock, Users, TrendingUp, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { EventCard } from '../components/EventCard';
import { EventModal } from '../components/EventModal';
import { ProfileModal } from '../components/ProfileModal';
import { SearchBar } from '../components/SearchBar';
import { Event } from '../types/Event';
import { eventService } from '../services/api';

export const AdminPanel: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const [editingEvent, setEditingEvent] = useState<Event | null>(null);

  const { logout, user } = useAuth();

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      setLoading(true);
      console.log('🔄 Carregando eventos admin...');
      const response = await eventService.getEventsAdmin();
      console.log('✅ Eventos admin carregados:', response.data);
      setEvents(response.data);
      setError(null);
    } catch (err) {
      console.error('Erro ao carregar eventos:', err);
      setError(`Erro ao carregar eventos: ${err instanceof Error ? err.message : 'Erro desconhecido'}`);
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

  const getRoleIcon = (role: string) => {
    return role === 'ADMIN' ? <Shield size={16} className="text-orange-400" /> : <User size={16} className="text-blue-400" />;
  };

  const getRoleLabel = (role: string) => {
    return role === 'ADMIN' ? 'Administrador' : 'Usuário';
  };

  // Estatísticas dos eventos
  const totalEvents = events.length;
  const upcomingEvents = events.filter(event => new Date(event.data) >= new Date()).length;
  const pastEvents = events.filter(event => new Date(event.data) < new Date()).length;
  const todayEvents = events.filter(event => {
    const today = new Date().toISOString().split('T')[0];
    return event.data === today;
  }).length;

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      {/* Header */}
      <header className="bg-slate-900/80 backdrop-blur-sm border-b border-slate-700/50 sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center gap-4">
              <div className="flex items-center justify-center w-10 h-10 bg-gradient-to-r from-orange-500 to-orange-600 rounded-lg">
                <Calendar className="text-white" size={20} />
              </div>
              <div>
                <h1 className="text-xl font-semibold text-white">Painel Administrativo</h1>
                <div className="flex items-center gap-2 text-xs text-slate-400">
                  {getRoleIcon(user?.role || '')}
                  <span>Bem-vindo, {user?.nome} ({getRoleLabel(user?.role || '')})</span>
                </div>
              </div>
            </div>

            <div className="flex items-center gap-3">
              <Link
                to="/"
                className="flex items-center gap-2 px-4 py-2 bg-slate-700/50 text-slate-300 rounded-lg hover:bg-slate-600/50 hover:text-white transition-all duration-200 border border-slate-600/50"
              >
                <Home size={18} />
                <span className="hidden sm:inline">Página Pública</span>
              </Link>
              
              <button
                onClick={() => setIsProfileModalOpen(true)}
                className="flex items-center gap-2 px-4 py-2 bg-slate-700/50 text-slate-300 rounded-lg hover:bg-slate-600/50 hover:text-white transition-all duration-200 border border-slate-600/50"
              >
                <User size={18} />
                <span className="hidden sm:inline">Perfil</span>
              </button>
              
              <button
                onClick={logout}
                className="flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-red-600 to-red-700 text-white rounded-lg hover:from-red-700 hover:to-red-800 transition-all duration-200 shadow-lg hover:shadow-red-500/25"
              >
                <LogOut size={18} />
                <span className="hidden sm:inline">Sair</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Dashboard Stats */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div className="bg-slate-800/50 backdrop-blur-sm rounded-xl p-6 border border-slate-700/50 hover:border-orange-500/30 transition-all duration-300">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-slate-400 text-sm font-medium">Total de Eventos</p>
                <p className="text-2xl font-bold text-white">{totalEvents}</p>
              </div>
              <div className="p-3 bg-orange-500/20 rounded-lg">
                <Calendar className="text-orange-400" size={24} />
              </div>
            </div>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-sm rounded-xl p-6 border border-slate-700/50 hover:border-green-500/30 transition-all duration-300">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-slate-400 text-sm font-medium">Próximos Eventos</p>
                <p className="text-2xl font-bold text-white">{upcomingEvents}</p>
              </div>
              <div className="p-3 bg-green-500/20 rounded-lg">
                <TrendingUp className="text-green-400" size={24} />
              </div>
            </div>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-sm rounded-xl p-6 border border-slate-700/50 hover:border-blue-500/30 transition-all duration-300">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-slate-400 text-sm font-medium">Eventos Hoje</p>
                <p className="text-2xl font-bold text-white">{todayEvents}</p>
              </div>
              <div className="p-3 bg-blue-500/20 rounded-lg">
                <Clock className="text-blue-400" size={24} />
              </div>
            </div>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-sm rounded-xl p-6 border border-slate-700/50 hover:border-purple-500/30 transition-all duration-300">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-slate-400 text-sm font-medium">Eventos Passados</p>
                <p className="text-2xl font-bold text-white">{pastEvents}</p>
              </div>
              <div className="p-3 bg-purple-500/20 rounded-lg">
                <BarChart3 className="text-purple-400" size={24} />
              </div>
            </div>
          </div>
        </div>

        {/* Actions Section */}
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-8">
          <div>
            <h2 className="text-2xl font-bold text-white mb-2">Gerenciar Eventos</h2>
            <p className="text-slate-400">Crie, edite e organize seus eventos</p>
          </div>
          <button
            onClick={handleNewEvent}
            className="flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-green-600 to-green-700 text-white rounded-lg hover:from-green-700 hover:to-green-800 transition-all duration-200 shadow-lg hover:shadow-green-500/25 font-medium"
          >
            <Plus size={20} />
            Novo Evento
          </button>
        </div>

        {/* Search Bar */}
        <div className="mb-8">
          <SearchBar onSearch={handleSearch} />
        </div>

        {/* Events Grid */}
        {loading ? (
          <div className="flex flex-col items-center justify-center py-20">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-orange-400 mb-4"></div>
            <p className="text-slate-400">Carregando eventos...</p>
          </div>
        ) : error ? (
          <div className="text-center py-20">
            <div className="bg-red-500/10 border border-red-500/20 rounded-xl p-8 max-w-md mx-auto">
              <Calendar className="mx-auto text-red-400 mb-4" size={48} />
              <h3 className="text-lg font-semibold text-red-400 mb-2">Erro ao Carregar</h3>
              <p className="text-red-300 mb-4">{error}</p>
              <button
                onClick={loadEvents}
                className="px-6 py-3 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
              >
                Tentar Novamente
              </button>
            </div>
          </div>
        ) : events.length === 0 ? (
          <div className="text-center py-20">
            <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700/50 rounded-xl p-12 max-w-md mx-auto">
              <Calendar className="mx-auto text-slate-500 mb-6" size={64} />
              <h3 className="text-xl font-semibold text-slate-400 mb-3">
                Nenhum evento encontrado
              </h3>
              <p className="text-slate-500 mb-6">
                Não há eventos cadastrados no momento.
              </p>
              <button
                onClick={handleNewEvent}
                className="flex items-center gap-2 mx-auto px-6 py-3 bg-gradient-to-r from-green-600 to-green-700 text-white rounded-lg hover:from-green-700 hover:to-green-800 transition-all duration-200 shadow-lg"
              >
                <Plus size={18} />
                Criar Primeiro Evento
              </button>
            </div>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
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

      {/* Modals */}
      <EventModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onSave={editingEvent ? handleUpdateEvent : handleCreateEvent}
        event={editingEvent}
      />

      <ProfileModal
        isOpen={isProfileModalOpen}
        onClose={() => setIsProfileModalOpen(false)}
      />
    </div>
  );
};