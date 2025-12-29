import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface Comentario {
  idComentario: number;
  contenido: string;
  esVisible: boolean;
  idComentrioPadre: number;
  idJuego: number;
  mail: string;
  
  respuestas?: Comentario[];
}


@Injectable({
  providedIn: 'root'
})
export class ObtenerComentariosPorJuegoService {

  constructor(private http: HttpClient) {}

  obtenerComentarios(idJuego: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(
      `${API_BASE_URL}/obtenerComentariosPorJuego`,
      { params: { idJuego } }
    );
  }
}
