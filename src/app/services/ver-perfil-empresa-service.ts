import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

//tipos
export interface EmpresaPerfil {
  idEmpresa: number;
  nombreEmpresa: string;
}

export interface JuegoEmpresaPerfil {
  id: number;
  nombreJuego: string;
  descripcion: string;
  especificaciones: string;
  clasificacion: number;
  categoria: number;
  empresa: number;
  precio: number;
  enVenta: boolean;
  urlImagen: string;
}

export interface PerfilEmpresaResponse {
  empresa: EmpresaPerfil;
  catalogo?: JuegoEmpresaPerfil[];
  totalJuegos?: number;
}


@Injectable({
  providedIn: 'root'
})
export class VerPerfilEmpresaService {

  constructor(private http: HttpClient) {}

  obtenerPerfilEmpresa(idEmpresa: number): Observable<PerfilEmpresaResponse | EmpresaPerfil> {
    return this.http.get<PerfilEmpresaResponse | EmpresaPerfil>(
      `${API_BASE_URL}/obtenerPerfilEmpresa`,
      { params: { idEmpresa } }
    );
  }
}
