import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface ReporteUsuarioResponse<T> {
  success: boolean;
  mail: string;
  tipo: string;
  data: T;
  cantidad: number;
}

export interface JuegoCalificadoUsuario {
  idJuego: number;
  nombre: string;
  promedio: number;
  portada: string;
}

export interface CalificacionPersonal {
  idJuego: number;
  nombre: string;
  calificacion: number;
  portada: string;
}

export interface ClasificacionFavorita {
  clasificacion: string;
  cantidad: number;
}

export interface JuegoPrestado {
  nombre: string;
  cantidad: number;
  portada: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReportesUsuarioComunService {

  constructor(private http: HttpClient) {}

  obtenerReporte<T>(
    mail: string,
    tipo: string
  ): Observable<ReporteUsuarioResponse<T>> {

    return this.http.get<ReporteUsuarioResponse<string[][]>>(
      `${API_BASE_URL}/reportesUsuario?mail=${mail}&tipo=${tipo}`
    ).pipe(
      map(resp => ({
        ...resp,
        data: this.mapearPorTipo(resp.data, resp.tipo) as T
      }))
    );
  }

  private mapearPorTipo(datos: string[][], tipo: string) {

    switch (tipo) {

      case 'mejoresCalificadosBiblioteca':
      case 'prestadosMejorCalificados':
        return datos.map(row => ({
          idJuego: Number(row[0]),
          nombre: row[1],
          promedio: Number(row[2].replace(',', '.')),
          portada: row[3]
        }));

      case 'calificacionesPersonales':
        return datos.map(row => ({
          idJuego: Number(row[0]),
          nombre: row[1],
          calificacion: Number(row[2]),
          portada: row[3]
        }));

      case 'clasificacionesFavoritas':
        return datos.map(row => ({
          clasificacion: row[0],
          cantidad: Number(row[1])
        }));

      case 'prestadosMasTiempo':
        return datos.map(row => ({
          nombre: row[0],
          cantidad: Number(row[1]),
          portada: row[2]
        }));

      default:
        return [];
    }
  }
}
