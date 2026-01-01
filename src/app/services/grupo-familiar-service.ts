import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface GrupoFamiliar {
  idGrupoFamiliar: number;
  nombreGrupo: string;
  integrantes: IntegranteGrupo[];
}

export interface IntegranteGrupo {
  mail: string;
  nickname: string;
  telefono: number;
}

@Injectable({
  providedIn: 'root'
})
export class GrupoFamiliarService {

  constructor(private http: HttpClient) {}

  obtenerGruposPorUsuario(mail: string): Observable<GrupoFamiliar[]> {
    return this.http.get<GrupoFamiliar[]>(
      `${API_BASE_URL}/obtenerGruposPorUsuario`,
      { params: { mail } }
    );
  }

  registrarUsuarioEnGrupo(mail: string, id: number): Observable<any> {
    const body = new HttpParams()
      .set('mail', mail)
      .set('idGrupo', id);

    return this.http.post(`${API_BASE_URL}/registrarUsuarioGrupo`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }

  sacarUsuarioDelGrupo(idGrupo: number, mail: string): Observable<any> {
    const body = new HttpParams()
      .set('idGrupo', idGrupo.toString())
      .set('mail', mail);

    return this.http.post(`${API_BASE_URL}/sacarUsuarioGrupoFamiliar`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }

  obtenerIntegrantes(idGrupo: number): Observable<IntegranteGrupo[]> {
    return this.http.get<IntegranteGrupo[]>(
      `${API_BASE_URL}/obtenerIntegrantesGrupo`,
      { params: { idGrupo: idGrupo.toString() } }
    );
  }

  crearGrupoFamiliar(mail: string, nombreGrupo: string): Observable<any> {
    const body = new HttpParams()
      .set('mail', mail)
      .set('nombreGrupo', nombreGrupo);

    return this.http.post(`${API_BASE_URL}/registroGrupoFamiliar`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }
}