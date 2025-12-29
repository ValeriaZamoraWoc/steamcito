import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface RespuestaInstalacion {
  instalado: boolean;
  mensaje?: string;
  encontrado?: boolean;
  detalle?: string;
  grupos?: string[];
}


@Injectable({
  providedIn: 'root'
})
export class JuegoAccionesService {

  constructor(private http: HttpClient) {}

  comprarJuego(nombreJuego: string, mail: string): Observable<string> {
    const params = new HttpParams()
      .set('juego', nombreJuego)
      .set('mail', mail);

    return this.http.post(
      `${API_BASE_URL}/registrarCompra`,
      params,
      { responseType: 'text' }
    );
  }

  instalarJuego(nombreJuego: string, mail: string): Observable<RespuestaInstalacion> {
    const params = new HttpParams()
      .set('juego', nombreJuego)
      .set('mail', mail);

    return this.http.post<RespuestaInstalacion>(
      `${API_BASE_URL}/instalarJuego`,
      params
    );
  }
  desinstalarJuego(idJuego: number, mail: string) {
    const body = new URLSearchParams();
    body.set('idJuego', idJuego.toString());
    body.set('mail', mail);

    return this.http.post<any>(
      `${API_BASE_URL}/desinstalarJuego`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
    );
  }

}
