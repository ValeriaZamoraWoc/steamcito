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
export class CompanyService {

  constructor(private http: HttpClient) {}

  obtenerCatalogo(idEmpresa: number): Observable<JuegoEmpresa[]> {
    return this.http.get<JuegoEmpresa[]>(
      `${API_BASE_URL}/obtenerCatalogoEmpresa`,
      { params: { idEmpresa } }
    );
  }

  obtenerPerfilEmpresa(idEmpresa: number): Observable<PerfilEmpresaResponse | EmpresaPerfil> {
    return this.http.get<PerfilEmpresaResponse | EmpresaPerfil>(
      `${API_BASE_URL}/obtenerPerfilEmpresa`,
      { params: { idEmpresa } }
    );
  }
}
