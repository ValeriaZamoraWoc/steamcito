import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../core/api.config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrarJuegoService {

  constructor(private http: HttpClient) {}

  registrarJuego(data: {
    nombre: string;
    descripcion: string;
    especificaciones: string;
    clasificacion: string;
    categoria: string;
    empresa: string;
    precio: number;
    fechaLanzamiento: string;
  }):  Observable<{ idJuego: number; mensaje: string }>{

    const body = new URLSearchParams();
    body.set('nombre', data.nombre);
    body.set('descripcion', data.descripcion);
    body.set('especificaciones', data.especificaciones);
    body.set('clasificacion', data.clasificacion);
    body.set('categoria', data.categoria);
    body.set('empresa', data.empresa.toString()); 
    body.set('precio', data.precio.toString());
    body.set('fechaLanzamiento', data.fechaLanzamiento);

    return this.http.post<{ idJuego: number; mensaje: string }>(
      `${API_BASE_URL}/registroJuego`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
    );
  }
}
