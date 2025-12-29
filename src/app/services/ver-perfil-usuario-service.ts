import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

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

@Injectable({
  providedIn: 'root'
})
export class ObtenerPerfilUsuarioService {

  constructor(private http: HttpClient) {}

  obtenerPerfil(mail: string): Observable<PerfilUsuarioRespuesta> {
    return this.http.get<PerfilUsuarioRespuesta>(
      `${API_BASE_URL}/obtenerPerfilUsuarioComun`,
      { params: { mail } }
    );
  }
}
