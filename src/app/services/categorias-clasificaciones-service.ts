import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../core/api.config';
import { Observable } from 'rxjs';

export interface Categoria {
  idCategoria: number;
  nombreCategoria: string;
  edadCategoria: number;
}

export interface Clasificacion {
  idClasificacion: number;
  nombreCalsificacion: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoriasClasificacionesService {

  constructor(private http: HttpClient) {}

  obtenerCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(
      `${API_BASE_URL}/obtenerCategorias`
    );
  }

  obtenerClasificaciones(): Observable<Clasificacion[]> {
    return this.http.get<Clasificacion[]>(
      `${API_BASE_URL}/obtenerClasificaciones`
    );
  }
  agregarClasificacion(nombre: string): Observable<any> {
  const body = new URLSearchParams();
  body.set('nombre', nombre);

  return this.http.post(
    `${API_BASE_URL}/registroClasificacion`,
    body.toString(),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  );
}

eliminarClasificacion(idClasificacion: number): Observable<any> {
  const body = new URLSearchParams();
  body.set('idClasificacion', idClasificacion.toString());

  return this.http.post(
    `${API_BASE_URL}/eliminarClasificacionAdmin`,
    body.toString(),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  );
}
cambiarClasificacionJuego(
  idJuego: number,
  idClasificacion: number
) {
  const body = new URLSearchParams();
  body.set('idJuego', idJuego.toString());
  body.set('idClasificacion', idClasificacion.toString());

  return this.http.post(
    `${API_BASE_URL}/cambiarClasificacionAdmin`,
    body.toString(),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      responseType: 'text'
    }
  );
}

}
