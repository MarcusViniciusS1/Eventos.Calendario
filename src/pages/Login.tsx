import React, { useState } from 'react';
import { Calendar, Lock, ArrowLeft, User, Key, Shield } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { eventService } from '../services/api';

export const Login: React.FC = () => {
  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      console.log('🔄 Simulando login para:', credentials.username);
      const response = await eventService.login(credentials);
      console.log('✅ Login simulado bem-sucedido:', response.data);
      
      login(response.data.token, response.data.username);
      navigate('/admin');
    } catch (err: any) {
      console.error('❌ Erro no login simulado:', err);
      // Mesmo com erro, vamos permitir o acesso
      login('fake-token', credentials.username || 'admin@admin.com');
      navigate('/admin');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value
    });
  };

  // Função para acesso direto
  const handleDirectAccess = () => {
    login('fake-token', 'admin@admin.com');
    navigate('/admin');
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        {/* Header com logo */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-20 h-20 bg-gradient-to-r from-orange-500 to-orange-600 rounded-full mb-6 shadow-lg">
            <Calendar className="text-white" size={32} />
          </div>
          <h1 className="text-3xl font-bold text-white mb-2">
            Calendário de Eventos
          </h1>
          <div className="flex items-center justify-center gap-2 text-slate-400">
            <Shield size={16} />
            <span className="text-sm">Sistema Simplificado</span>
          </div>
        </div>

        {/* Card de login */}
        <div className="bg-slate-800/50 backdrop-blur-sm rounded-2xl p-8 border border-slate-700/50 shadow-2xl">
          <div className="text-center mb-8">
            <div className="inline-flex items-center justify-center w-12 h-12 bg-orange-600/20 rounded-full mb-4">
              <Lock className="text-orange-400" size={20} />
            </div>
            <h2 className="text-xl font-semibold text-white">
              Acesso Administrativo
            </h2>
            <p className="text-slate-400 text-sm mt-2">
              Apenas para pessoas autorizadas
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label className="block text-slate-300 text-sm font-medium mb-3">
                Email (Opcional)
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <User className="h-5 w-5 text-slate-400" />
                </div>
                <input
                  type="email"
                  name="username"
                  value={credentials.username}
                  onChange={handleChange}
                  className="w-full pl-10 pr-4 py-3 bg-slate-700/50 border border-slate-600/50 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
                  placeholder="Digite qualquer email"
                />
              </div>
            </div>

            <div>
              <label className="block text-slate-300 text-sm font-medium mb-3">
                Senha (Opcional)
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Key className="h-5 w-5 text-slate-400" />
                </div>
                <input
                  type="password"
                  name="password"
                  value={credentials.password}
                  onChange={handleChange}
                  className="w-full pl-10 pr-4 py-3 bg-slate-700/50 border border-slate-600/50 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
                  placeholder="Digite qualquer senha"
                />
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full py-3 bg-gradient-to-r from-orange-600 to-orange-700 text-white rounded-lg hover:from-orange-700 hover:to-orange-800 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed font-medium shadow-lg hover:shadow-orange-500/25"
            >
              {loading ? (
                <div className="flex items-center justify-center gap-2">
                  <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></div>
                  Entrando...
                </div>
              ) : (
                <div className="flex items-center justify-center gap-2">
                  <Shield size={18} />
                  Entrar
                </div>
              )}
            </button>
          </form>


          <div className="mt-6">
            <Link
              to="/"
              className="w-full flex items-center justify-center gap-2 py-3 bg-slate-700/50 text-slate-300 rounded-lg hover:bg-slate-600/50 transition-all duration-200 border border-slate-600/50"
            >
              <ArrowLeft size={18} />
              Voltar ao Calendário
            </Link>
          </div>
        </div>

        {/* Footer */}
        <div className="text-center mt-8">
          <div className="flex items-center justify-center gap-2 text-slate-500 text-sm">
            <Shield size={14} />
            <span>Sistema sem autenticação JWT</span>
          </div>
        </div>
      </div>
    </div>
  );
};