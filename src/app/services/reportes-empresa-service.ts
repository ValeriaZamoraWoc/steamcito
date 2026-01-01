import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface ReporteEmpresaResponse<T> {
  exito: boolean;
  idEmpresa: number;
  tipo: string;
  datos: T;
}

export interface VentaEmpresa {
  nombreJuego: string;
  precio: number;
  porcentaje: number;
  comision: number;
  ingresosNetos: number;
}
export interface JuegoCalificado {
  idJuego: number;
  nombre: string;
  promedio: number;
  totalResenas: number;
  portada: string;
}
export interface TopJuego extends JuegoCalificado {}

@Injectable({
  providedIn: 'root'
})
export class ReportesEmpresaService {

  constructor(private http: HttpClient) {}

  obtenerReporte<T>(
    idEmpresa: number,
    tipo: string
  ): Observable<ReporteEmpresaResponse<T>> {

    return this.http.get<ReporteEmpresaResponse<string[][]>>(
      `${API_BASE_URL}/reportesEmpresa?idEmpresa=${idEmpresa}&tipo=${tipo}`
    ).pipe(
      map(resp => ({
        ...resp,
        datos: this.mapearPorTipo(resp.datos, resp.tipo) as T
      }))
    );
  }
  obtenerMejoresComentarios(idEmpresa: number): Observable<any> {
    return this.http.get(
      `${API_BASE_URL}/reportesEmpresa?idEmpresa=${idEmpresa}&tipo=mejoresComentarios`
    );
  }
  private mapearPorTipo(datos: string[][], tipo: string) {
    switch (tipo) {

      case 'ventas':
      return datos.map(row => ({
        nombreJuego: row[0],
        precio: Number(row[1]),    
        porcentaje: row[2],    
        comision: Number(row[3]),      
        ingresosNetos: Number(row[4])    
      }));
      case 'mejorCalificados':
      case 'peorCalificados':
      case 'top5':
        return datos.map(row => ({
          idJuego: Number(row[0]),
          nombre: row[1],
          promedio: Number(row[2].replace(',', '.')),
          totalResenas: Number(row[3]),
          portada: row[4]
        }));

      default:
        return [];
    }
  }
}


