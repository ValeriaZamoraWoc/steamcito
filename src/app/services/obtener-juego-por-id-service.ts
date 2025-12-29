import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { Juego } from './obtener-todos-los-juegos';
import { JuegoEmpresaPerfil } from './ver-perfil-empresa-service';

@Injectable({
  providedIn: 'root'
})
export class ObtenerJuegoPorIdService {

  constructor(private http: HttpClient) {}

  obtenerJuego(id: number): Observable<Juego> {
    return this.http.get<Juego>(
      `${API_BASE_URL}/obtenerJuegoPorId`,
      { params: { idJuego: id } }
    );
  }
  suspenderVenta(nombreJuego: string, idEmpresa: string) {
    const params = new HttpParams()
      .set('nombreJuego', nombreJuego)
      .set('idEmpresa', idEmpresa);

    return this.http.post(
      `${API_BASE_URL}/suspenderVentaJuego`,
      null,
      { params, responseType: 'text' }
    );
  }
  actualizarJuego(data: {
  idJuego: number;
  nombreJuego: string;
  descripcion: string;
  especificaciones: string;
  clasificacion: number;
  categoria: number;
  precio: number;
  fechaLanzamiento: string;
}) {
  const params = new HttpParams()
    .set('idJuego', data.idJuego)
    .set('nombreJuego', data.nombreJuego)
    .set('descripcion', data.descripcion)
    .set('especificaciones', data.especificaciones)
    .set('clasificacion', data.clasificacion)
    .set('categoria', data.categoria)
    .set('precio', data.precio)
    .set('fechaLanzamiento', data.fechaLanzamiento);

  return this.http.post(
    `${API_BASE_URL}/actualizarJuego`,
    params,
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  );
}

  obtenerJuegoPorId(id: number) {
    return this.http.get<JuegoEmpresaPerfil>(
      `${API_BASE_URL}/obtenerJuegoPorId`,
      { params: { idJuego:id } }
    );
  }
  subirImagenJuego(form: FormData) {
  return this.http.post(
    `${API_BASE_URL}/guardarImagenJuego`,
    form
  );
}
}
