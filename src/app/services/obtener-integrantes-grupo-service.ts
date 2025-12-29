import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface IntegranteGrupo {
  mail: string;
  nickname: string;
  telefono: number;
}

@Injectable({
  providedIn: 'root'
})
export class ObtenerIntegrantesGrupoService {

  constructor(private http: HttpClient) {}

  obtenerIntegrantes(idGrupo: number): Observable<IntegranteGrupo[]> {
    return this.http.get<IntegranteGrupo[]>(
      `${API_BASE_URL}/obtenerIntegrantesGrupo`,
      { params: { idGrupo } }
    );
  }
}
