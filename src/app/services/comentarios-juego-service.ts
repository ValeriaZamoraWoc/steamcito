import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class ComentariosJuegoService {

  constructor(private http: HttpClient) {}

  comentarJuego(
    descripcion: string,
    nombreJuego: string,
    mail: string,
    calificacion?: number
  ): Observable<void> {

    let params = new HttpParams()
      .set('descripcion', descripcion)
      .set('nombreJuego', nombreJuego)
      .set('mail', mail);

    if (calificacion !== undefined) {
      params = params.set('calificacion', calificacion.toString());
    }

    return this.http.post<void>(
      `${API_BASE_URL}/comentarClaificarJuego`,
      params
    );
  }

  comentarComentario(
    descripcion: string,
    nombreJuego: string,
    mail: string,
    idComentarioPadre: number,
    calificacion?: number
  ): Observable<void> {

    let params = new HttpParams()
      .set('descripcion', descripcion)
      .set('nombreJuego', nombreJuego)
      .set('mail', mail)
      .set('idComentarioPadre', idComentarioPadre.toString());

    if (calificacion !== undefined) {
      params = params.set('calificacion', calificacion.toString());
    }

    return this.http.post<void>(
      `${API_BASE_URL}/comentarComentario`,
      params
    );
  }
}
