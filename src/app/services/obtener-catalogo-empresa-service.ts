import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface JuegoEmpresa {
  idJuego: number;
  nombre: string;
  fechaLanzamiento: string;
  precio: number;
}

@Injectable({
  providedIn: 'root'
})
export class CatalogoEmpresaService {

  constructor(private http: HttpClient) {}

  obtenerCatalogo(idEmpresa: number): Observable<JuegoEmpresa[]> {
    return this.http.get<JuegoEmpresa[]>(
      `${API_BASE_URL}/obtenerCatalogoEmpresa`,
      { params: { idEmpresa } }
    );
  }
}
