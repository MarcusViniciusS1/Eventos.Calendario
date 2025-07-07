import React, { useState } from 'react';
import { Calendar, Lock, ArrowLeft, User, Key, Shield, UserPlus, AlertCircle } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export const Login: React.FC = () => {
  const [isLoginMode, setIsLoginMode] = useState(true);
  const [credentials, setCredentials] = useState({
    email: '',
    password: '',
    nome: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (isLoginMode) {
        console.log('Tentando login com:', credentials.email);
        
        // Login
        const success = await login(credentials.email, credentials.password);
        if (success) {
          console.log('Login bem-sucedido, redirecionando...');
          navigate('/admin');
        } else {
          setError('Email ou senha incorretos');
        }
      } else {
        // Registro
        console.log('Tentando registro com:', credentials.email);
        
        const response = await fetch('http://localhost:8080/api/auth/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            nome: credentials.nome,
            email: credentials.email,
            senha: credentials.password
          }),
        });

        if (response.ok) {
          const data = await response.json();
          console.log('Registro bem-sucedido:', data);
          setIsLoginMode(true);
          setError('');
          setCredentials({ email: credentials.email, password: '', nome: '' });
          alert('Conta criada com sucesso! Faça login agora.');
        } else {
          const errorData = await response.json();
          console.error('Erro no registro:', errorData);
          setError(errorData.message || 'Erro ao criar conta');
        }
      }
    } catch (err: any) {
      console.error('Erro na autenticação:', err);
      
      if (err.message && err.message.includes('Network Error')) {
        setError('Erro de conexão: Verifique se o backend está rodando na porta 8080');
      } else {
        setError(isLoginMode ? 'Erro ao fazer login. Verifique sua conexão.' : 'Erro ao criar conta. Verifique sua conexão.');
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

  const toggleMode = () => {
    setIsLoginMode(!isLoginMode);
    setError('');
    setCredentials({ email: '', password: '', nome: '' });
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
            <span className="text-sm">Sistema com Autenticação JWT</span>
          </div>
        </div>

        {/* Card de login/registro */}
        <div className="bg-slate-800/50 backdrop-blur-sm rounded-2xl p-8 border border-slate-700/50 shadow-2xl">
          <div className="text-center mb-8">
            <div className="inline-flex items-center justify-center w-12 h-12 bg-orange-600/20 rounded-full mb-4">
              {isLoginMode ? <Lock className="text-orange-400" size={20} /> : <UserPlus className="text-orange-400" size={20} />}
            </div>
            <h2 className="text-xl font-semibold text-white">
              {isLoginMode ? 'Acesso Administrativo' : 'Criar Nova Conta'}
            </h2>
            <p className="text-slate-400 text-sm mt-2">
              {isLoginMode ? 'Entre com suas credenciais' : 'Registre-se para acessar o sistema'}
            </p>
          </div>

          {error && (
            <div className="mb-6 p-4 bg-red-500/10 border border-red-500/20 rounded-lg flex items-start gap-3">
              <AlertCircle className="text-red-400 flex-shrink-0 mt-0.5" size={16} />
              <p className="text-red-400 text-sm">{error}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-6">
            {!isLoginMode && (
              <div>
                <label className="block text-slate-300 text-sm font-medium mb-3">
                  Nome Completo
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <User className="h-5 w-5 text-slate-400" />
                  </div>
                  <input
                    type="text"
                    name="nome"
                    value={credentials.nome}
                    onChange={handleChange}
                    required={!isLoginMode}
                    className="w-full pl-10 pr-4 py-3 bg-slate-700/50 border border-slate-600/50 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
                    placeholder="Digite seu nome completo"
                  />
                </div>
              </div>
            )}

            <div>
              <label className="block text-slate-300 text-sm font-medium mb-3">
                Email
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <User className="h-5 w-5 text-slate-400" />
                </div>
                <input
                  type="email"
                  name="email"
                  value={credentials.email}
                  onChange={handleChange}
                  required
                  className="w-full pl-10 pr-4 py-3 bg-slate-700/50 border border-slate-600/50 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
                  placeholder="Digite seu email"
                />
              </div>
            </div>

            <div>
              <label className="block text-slate-300 text-sm font-medium mb-3">
                Senha
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
                  required
                  className="w-full pl-10 pr-4 py-3 bg-slate-700/50 border border-slate-600/50 rounded-lg text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-orange-500/50 focus:border-orange-500/50 transition-all duration-200"
                  placeholder="Digite sua senha"
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
                  {isLoginMode ? 'Entrando...' : 'Criando conta...'}
                </div>
              ) : (
                <div className="flex items-center justify-center gap-2">
                  {isLoginMode ? <Shield size={18} /> : <UserPlus size={18} />}
                  {isLoginMode ? 'Entrar' : 'Criar Conta'}
                </div>
              )}
            </button>
          </form>

          {/* Toggle entre login e registro */}
          <div className="mt-6 text-center">
            <button
              onClick={toggleMode}
              className="text-orange-400 hover:text-orange-300 transition-colors text-sm"
            >
              {isLoginMode 
                ? 'Não tem uma conta? Criar nova conta' 
                : 'Já tem uma conta? Fazer login'
              }
            </button>
          </div>

          {/* Botão para voltar */}
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
            <span>Sistema protegido com JWT</span>
          </div>
        </div>
      </div>
    </div>
  );
};