import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { Juego } from './game-service';

@Injectable({
  providedIn: 'root'
})
export class BannerService {

  constructor(private http: HttpClient) {}

  obtenerBanner(): Observable<Juego> {
    return this.http.get<Juego>(`${API_BASE_URL}/obtenerBanner`);
  }
  editarBanner(idJuegoNuevo: number): Observable<any> {
    const body = new HttpParams()
      .set('idJuegoNuevo', idJuegoNuevo.toString());

    return this.http.post(
      `${API_BASE_URL}/editarBanner`,
      body.toString(),
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
  }
}
