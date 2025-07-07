import React, { useState } from 'react';
<<<<<<< HEAD
import { Calendar, Lock, ArrowLeft, User, Key, Shield } from 'lucide-react';
=======
import { Lock, ArrowLeft } from 'lucide-react';
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { eventService } from '../services/api';

export const Login: React.FC = () => {
  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
<<<<<<< HEAD
  
=======
  const [error, setError] = useState<string | null>(null);

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
<<<<<<< HEAD

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
=======
    setError(null);

    try {
      const response = await eventService.login(credentials);
      login(response.data.token, response.data.username);
      navigate('/admin');
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Erro ao fazer login');
      }
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
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

<<<<<<< HEAD
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
              Sistema sem autenticação JWT
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
                  Entrar com Auth0 JWT
                </div>
              )}
            </button>
          </form>

          {/* Acesso direto */}
          <div className="mt-6 p-4 bg-slate-700/30 rounded-lg border border-slate-600/30">
            <div className="flex items-center gap-2 mb-3">
              <Shield size={16} className="text-green-400" />
              <p className="text-slate-300 text-sm font-medium">Acesso Direto:</p>
            </div>
            
            <button
              onClick={handleDirectAccess}
              className="w-full text-left p-3 bg-green-600/20 hover:bg-green-600/30 rounded text-sm text-green-300 transition-colors border border-green-500/30"
            >
              <div className="flex items-center justify-between">
                <span className="font-medium">🚀 Entrar Diretamente</span>
                <span className="text-xs text-green-400">Sem autenticação</span>
              </div>
              <div className="text-xs text-green-400 mt-1">
                Clique aqui para acessar o painel administrativo
              </div>
            </button>
          </div>

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
      </div>
    </div>
  );
};
=======
  const handleTestLogin = () => {
    setCredentials({
      username: 'ADM',
      password: 'ADM123'
    });
  };

  return (
      <div className="min-h-screen bg-slate-800 flex items-center justify-center p-4">
        <div className="w-full max-w-md">
          <div className="bg-slate-700 rounded-lg p-8 border border-slate-600">
            <div className="text-center mb-8">
              <div className="inline-flex items-center justify-center w-16 h-16 bg-orange-600 rounded-full mb-4">
                <Lock className="text-white" size={24} />
              </div>
              <h1 className="text-2xl font-semibold text-orange-400 mb-2">
                Login Administrativo
              </h1>
            </div>

            {error && (
                <div className="bg-red-900 border border-red-700 text-red-300 px-4 py-3 rounded-lg mb-6">
                  {error}
                </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-6">
              <div>
                <label className="block text-slate-300 text-sm font-medium mb-2">
                  Usuário
                </label>
                <input
                    type="text"
                    name="username"
                    value={credentials.username}
                    onChange={handleChange}
                    required
                    className="w-full px-4 py-3 bg-slate-600 border border-slate-500 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent"
                    placeholder="Digite o usuário"
                />
              </div>

              <div>
                <label className="block text-slate-300 text-sm font-medium mb-2">
                  Senha
                </label>
                <input
                    type="password"
                    name="password"
                    value={credentials.password}
                    onChange={handleChange}
                    required
                    className="w-full px-4 py-3 bg-slate-600 border border-slate-500 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent"
                    placeholder="Digite a senha"
                />
              </div>

              <button
                  type="submit"
                  disabled={loading}
                  className="w-full py-3 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed font-medium"
              >
                {loading ? 'Entrando...' : 'Entrar'}
              </button>
            </form>

            <div className="mt-6">
              <Link
                  to="/"
                  className="w-full flex items-center justify-center gap-2 py-3 bg-slate-600 text-white rounded-lg hover:bg-slate-500 transition-colors"
              >
                <ArrowLeft size={18} />
                Voltar ao Calendário
              </Link>
            </div>

            <div className="mt-6 p-4 bg-slate-600 rounded-lg">
              <div className="flex justify-between items-center mb-3">
                <p className="text-slate-300 text-sm font-medium">Credenciais de teste:</p>
                <button
                    onClick={handleTestLogin}
                    className="text-xs bg-orange-600 hover:bg-orange-700 text-white px-3 py-1 rounded transition-colors"
                >
                  Usar credenciais
                </button>
              </div>
              <p className="text-slate-400 text-sm">Usuário: <span className="font-mono">ADM</span></p>
              <p className="text-slate-400 text-sm">Senha: <span className="font-mono">ADM123</span></p>
            </div>
          </div>
        </div>
      </div>
  );
};
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
