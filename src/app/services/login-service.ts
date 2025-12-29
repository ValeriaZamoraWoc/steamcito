import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { computed } from '@angular/core';


export type AuthUser = {
  mail: string;
  rol: string;
  idEmpresa?: number; 
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {

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
    localStorage.setItem('auth_user', JSON.stringify(usuario)); // 👈 CLAVE
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

}
