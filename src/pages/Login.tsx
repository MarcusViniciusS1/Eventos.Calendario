import React, { useState } from 'react';
import { Lock, ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { eventService } from '../services/api';

export const Login: React.FC = () => {
  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
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
