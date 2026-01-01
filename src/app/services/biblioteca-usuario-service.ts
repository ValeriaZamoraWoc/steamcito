import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { JuegoBiblioteca } from './user-service';

export interface RespuestaBiblioteca {
  visibilidad: boolean;
  biblioteca: JuegoBiblioteca[];
}

@Injectable({
  providedIn: 'root'
})
export class BibliotecaUsuarioService {

  constructor(private http: HttpClient) {}

  obtenerBiblioteca(mail: string): Observable<RespuestaBiblioteca> {
    return this.http.get<RespuestaBiblioteca>(
      `${API_BASE_URL}/obtenerBibliotecaUsuario`,
      { params: { mail } }
    );
  }
}
