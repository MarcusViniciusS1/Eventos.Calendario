export interface Event {
  id?: number;
  titulo: string;
  descricao: string;
  data: string;
  hora: string;
  local: string;
  organizador: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  username: string;
}