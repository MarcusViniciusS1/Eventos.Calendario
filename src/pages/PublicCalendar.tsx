import React, { useState, useEffect } from 'react';
import { Calendar, LogIn, Search, Filter, MapPin, User, Clock } from 'lucide-react';
import { Link } from 'react-router-dom';
import { EventCard } from '../components/EventCard';
import { Event } from '../types/Event';
import { eventService } from '../services/api';

export const PublicCalendar: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [filteredEvents, setFilteredEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState('all');

  useEffect(() => {
    loadEvents();
  }, []);

  useEffect(() => {
    filterEvents();
  }, [events, searchTerm, filterType]);

  const loadEvents = async () => {
    try {
      setLoading(true);
      console.log('🔄 Carregando eventos públicos...');
      const response = await eventService.getAllEvents();
      console.log('✅ Eventos carregados:', response.data);
      setEvents(response.data);
      setError(null);
    } catch (err) {
      console.error('Erro ao carregar eventos:', err);
      setError(`Erro ao carregar eventos: ${err instanceof Error ? err.message : 'Erro desconhecido'}`);
    } finally {
      setLoading(false);
    }
  };

  const filterEvents = () => {
    let filtered = events;

    // Filtro por termo de busca
    if (searchTerm) {
      filtered = filtered.filter(event =>
          event.titulo.toLowerCase().includes(searchTerm.toLowerCase()) ||
          event.descricao.toLowerCase().includes(searchTerm.toLowerCase()) ||
          event.organizador.toLowerCase().includes(searchTerm.toLowerCase()) ||
          event.local.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filtro por tipo/data
    if (filterType === 'today') {
      const today = new Date().toISOString().split('T')[0];
      filtered = filtered.filter(event => event.data === today);
    } else if (filterType === 'upcoming') {
      const today = new Date().toISOString().split('T')[0];
      filtered = filtered.filter(event => event.data >= today);
    } else if (filterType === 'past') {
      const today = new Date().toISOString().split('T')[0];
      filtered = filtered.filter(event => event.data < today);
    }

    setFilteredEvents(filtered);
  };

  const handleFilterChange = (type: string) => {
    setFilterType(type);
  };

  const clearFilters = () => {
    setSearchTerm('');
    setFilterType('all');
  };

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  return (
      <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
        <header className="bg-slate-900/80 backdrop-blur-sm border-b border-slate-700/50 sticky top-0 z-40">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex items-center justify-between py-4">
              <div className="flex items-center gap-4">
                <div className="flex items-center justify-center w-10 h-10 bg-gradient-to-r from-orange-500 to-orange-600 rounded-lg">
                  <Calendar className="text-white" size={20} />
                </div>
                <div>
                  <h1 className="text-xl font-semibold text-white">Calendário de Eventos</h1>
                  <p className="text-xs text-slate-400">Descubra eventos incríveis</p>
                </div>
              </div>
              <Link
                  to="/login"
                  className="flex items-center gap-2 px-6 py-2 bg-gradient-to-r from-orange-600 to-orange-700 text-white rounded-lg hover:from-orange-700 hover:to-orange-800 transition-all duration-200 shadow-lg hover:shadow-orange-500/25"
              >
                <LogIn size={18} />
                Área Admin
              </Link>
            </div>
          </div>
        </header>

        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          {/* Hero Section */}
          <div className="text-center mb-12">
            <h2 className="text-4xl font-bold text-white mb-4">
              Eventos em Destaque
            </h2>
            <p className="text-xl text-slate-400 max-w-2xl mx-auto">
              Descubra os melhores eventos da sua região. Filtre por categoria, data ou local para encontrar exatamente o que você procura.
            </p>
          </div>

          {/* Search and Filter Section */}
          <div className="mb-8 space-y-4">
            {/* Search Bar */}
            <div className="relative">
              <div className="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                <Search className="h-5 w-5 text-slate-400" />
              </div>
              <input
                  type="text"
                  placeholder="Buscar eventos por título, descrição, organizador ou local..."
                  value={searchTerm}
                  onChange={handleSearchChange}
                  className="w-full pl-12 pr-4 py-4 bg-slate-800/50 backdrop-blur-sm border border-slate-700/50 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
              />
            </div>

            {/* Filter Buttons */}
            <div className="flex flex-wrap gap-3 items-center">
              <div className="flex items-center gap-2 text-slate-300">
                <Filter size={16} />
                <span className="text-sm font-medium">Filtrar por:</span>
              </div>

              <div className="flex flex-wrap gap-2">
                {[
                  { key: 'all', label: 'Todos', icon: Calendar },
                  { key: 'today', label: 'Hoje', icon: Clock },
                  { key: 'upcoming', label: 'Próximos', icon: Calendar },
                  { key: 'past', label: 'Passados', icon: Calendar }
                ].map(({ key, label, icon: Icon }) => (
                    <button
                        key={key}
                        onClick={() => handleFilterChange(key)}
                        className={`flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                            filterType === key
                                ? 'bg-orange-600 text-white shadow-lg'
                                : 'bg-slate-700/50 text-slate-300 hover:bg-slate-600/50 hover:text-white'
                        }`}
                    >
                      <Icon size={14} />
                      {label}
                    </button>
                ))}
              </div>

              {(searchTerm || filterType !== 'all') && (
                  <button
                      onClick={clearFilters}
                      className="px-4 py-2 bg-slate-600/50 text-slate-300 rounded-lg hover:bg-slate-500/50 transition-colors text-sm"
                  >
                    Limpar Filtros
                  </button>
              )}
            </div>

            {/* Results Counter */}
            {!loading && (
                <div className="text-slate-400 text-sm">
                  {filteredEvents.length === events.length
                      ? `${events.length} evento${events.length !== 1 ? 's' : ''} encontrado${events.length !== 1 ? 's' : ''}`
                      : `${filteredEvents.length} de ${events.length} evento${events.length !== 1 ? 's' : ''} encontrado${filteredEvents.length !== 1 ? 's' : ''}`
                  }
                </div>
            )}
          </div>

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
                  <p className="text-red-300">{error}</p>
                  <button
                      onClick={loadEvents}
                      className="mt-4 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
                  >
                    Tentar Novamente
                  </button>
                </div>
              </div>
          ) : filteredEvents.length === 0 ? (
              <div className="text-center py-20">
                <div className="bg-slate-800/50 backdrop-blur-sm border border-slate-700/50 rounded-xl p-12 max-w-md mx-auto">
                  <Calendar className="mx-auto text-slate-500 mb-6" size={64} />
                  <h3 className="text-xl font-semibold text-slate-400 mb-3">
                    {searchTerm || filterType !== 'all' ? 'Nenhum evento encontrado' : 'Nenhum evento cadastrado'}
                  </h3>
                  <p className="text-slate-500 mb-6">
                    {searchTerm || filterType !== 'all'
                        ? 'Tente ajustar os filtros ou termo de busca.'
                        : 'Não há eventos cadastrados até o momento.'
                    }
                  </p>
                  {(searchTerm || filterType !== 'all') && (
                      <button
                          onClick={clearFilters}
                          className="px-6 py-3 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition-colors"
                      >
                        Ver Todos os Eventos
                      </button>
                  )}
                </div>
              </div>
          ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {filteredEvents.map((event) => (
                    <EventCard key={event.id} event={event} />
                ))}
              </div>
          )}
        </main>

        {/* Footer */}
        <footer className="bg-slate-900/50 backdrop-blur-sm border-t border-slate-700/50 mt-20">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
            <div className="text-center">
              <div className="flex items-center justify-center gap-3 mb-4">
                <div className="flex items-center justify-center w-10 h-10 bg-gradient-to-r from-orange-500 to-orange-600 rounded-lg">
                  <Calendar className="text-white" size={20} />
                </div>
                <h3 className="text-xl font-semibold text-white">Calendário de Eventos</h3>
              </div>
              <p className="text-slate-400 mb-6 max-w-2xl mx-auto">
                Sua plataforma completa para descobrir e gerenciar eventos.
                Conectando pessoas através de experiências únicas.
              </p>
              <div className="flex items-center justify-center gap-6 text-sm text-slate-500">
                <div className="flex items-center gap-2">
                  <MapPin size={14} />
                  <span>Eventos Locais</span>
                </div>
                <div className="flex items-center gap-2">
                  <User size={14} />
                  <span>Organizadores Verificados</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock size={14} />
                  <span>Atualizações em Tempo Real</span>
                </div>
              </div>
            </div>
          </div>
        </footer>
      </div>
  );
};