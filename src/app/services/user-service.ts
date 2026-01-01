import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { computed } from '@angular/core';
import { firstValueFrom } from 'rxjs';

export interface UsuarioComun {
  mail: string;
  nickname: string;
  pais: string;
}

export interface JuegoBiblioteca {
  id: number;
  nombreJuego: string;
  descripcion: string;
  especificaciones: string;
  clasificacion: number;
  categoria: number;
  empresa: number;
  precio: number;
  enVenta: boolean;
  fechaLanzamiento: string; 
  urlImagen: string;
  instalado: boolean;
}

export interface PerfilUsuarioRespuesta {
  visibilidad?: boolean;
  usuario?: UsuarioComun;
  biblioteca?: JuegoBiblioteca[];
}

export type AuthUser = {
  mail: string;
  rol: string;
  idEmpresa?: number; 
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user = signal<AuthUser | null>(null);
  rol = computed(() => this.user()?.rol);

  constructor(private http: HttpClient) {
    const guardado = localStorage.getItem('auth_user');
    if (guardado) {
      this.user.set(JSON.parse(guardado));
    }
  }

  async login(mail: string, password: string): Promise<void> {
    const body = new URLSearchParams();
    body.set('mail', mail);
    body.set('password', password);

    const usuario = await firstValueFrom(
      this.http.post<AuthUser>(
        `${API_BASE_URL}/login`,
        body.toString(),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
      )
    );

    this.user.set(usuario);
    localStorage.setItem('auth_user', JSON.stringify(usuario));
  }

  logout() {
    this.user.set(null);
    localStorage.removeItem('auth_user');
  }

  isLogged(): boolean {
    return this.user() !== null;
  }

  isAdmin(): boolean {
    return this.user()?.rol === 'dtoUsuario';
  }

  isComun(): boolean {
    return this.user()?.rol === 'dtoUsuarioComun';
  }

  isDesarrollador(): boolean {
    return this.user()?.rol === 'dtoUsuarioDesarrollador';
  }

  getMail(): string | null {
    return this.user()?.mail ?? null;
  }

  getIdEmpresa(): number | null {
    return this.user()?.idEmpresa ?? null;
  }

  obtenerPerfil(mail: string): Observable<PerfilUsuarioRespuesta> {
    return this.http.get<PerfilUsuarioRespuesta>(
      `${API_BASE_URL}/obtenerPerfilUsuarioComun`,
      { params: { mail } }
    );
  }

  registrarUsuario(data: {
    tipoUsuario: 'Comun' | 'Desarrollador' | 'Admin';
    mail: string;
    nickname: string;
    contrasena: string;
    fechaNacimiento: string;
    pais?: string;
    telefono?: number;
    idEmpresa?: number;
  }): Observable<string> {

    const body = new URLSearchParams();
    body.set('tipoUsuario', data.tipoUsuario);
    body.set('mail', data.mail);
    body.set('nickname', data.nickname);
    body.set('contrasena', data.contrasena);
    body.set('fechaNacimiento', data.fechaNacimiento);

    if (data.tipoUsuario === 'Comun') {
      body.set('pais', data.pais!);
      body.set('telefono', data.telefono!.toString());
    } else if (data.tipoUsuario === 'Desarrollador') {
      body.set('idEmpresa', data.idEmpresa!.toString());
    }

    return this.http.post(`${API_BASE_URL}/registrarUsuarios`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'text'
    });
  }

  registrarEmpresa(data: { nombre: string; descripcion: string }): Observable<string> {
    const body = new URLSearchParams();
    body.set('nombre', data.nombre);
    body.set('descripcion', data.descripcion);

    return this.http.post(`${API_BASE_URL}/registroEmpresa`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'text'
    });
  }

  registrarDesarrolladorEmpresa(data: {
    mail: string;
    nickname: string;
    contrasena: string;
    fechaNacimiento: string;
    empresa: number;
  }): Observable<string> {

    const body = new URLSearchParams();
    body.set('tipoUsuario', 'Desarrollador');
    body.set('mail', data.mail);
    body.set('nickname', data.nickname);
    body.set('contrasena', data.contrasena);
    body.set('fechaNacimiento', data.fechaNacimiento);
    body.set('idEmpresa', data.empresa.toString());

    return this.http.post(
      `${API_BASE_URL}/registrarUsuarios`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        responseType: 'text'
      }
    );
  }
}
