import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface GrupoFamiliar {
  idGrupoFamiliar: number;
  nombreGrupo: string;
}

@Injectable({
  providedIn: 'root'
})
export class GruposFamiliaresService {

  constructor(private http: HttpClient) {}

  obtenerGruposPorUsuario(mail: string): Observable<GrupoFamiliar[]> {
    return this.http.get<GrupoFamiliar[]>(
      `${API_BASE_URL}/obtenerGruposPorUsuario`,
      { params: { mail } }
    );
  }
}
