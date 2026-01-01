import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({ providedIn: 'root' })
export class RegistroService {

  constructor(private http: HttpClient) {}

  registrarUsuario(data: {
    tipoUsuario: 'Comun' | 'Desarrollador' | 'Admin';
    mail: string;
    nickname: string;
    contrasena: string;
    fechaNacimiento: string;
    pais?: string;
    telefono?: number;
    idEmpresa?: number;
  }): Observable<string> {

    const body = new URLSearchParams();
    body.set('tipoUsuario', data.tipoUsuario);
    body.set('mail', data.mail);
    body.set('nickname', data.nickname);
    body.set('contrasena', data.contrasena);
    body.set('fechaNacimiento', data.fechaNacimiento);

    if (data.tipoUsuario === 'Comun') {
      body.set('pais', data.pais!);
      body.set('telefono', data.telefono!.toString());
    } else if (data.tipoUsuario === 'Desarrollador') {
      body.set('idEmpresa', data.idEmpresa!.toString());
    }

    return this.http.post(`${API_BASE_URL}/registrarUsuarios`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'text'
    });
  }

  registrarEmpresa(data: { nombre: string; descripcion: string }): Observable<string> {
    const body = new URLSearchParams();
    body.set('nombre', data.nombre);
    body.set('descripcion', data.descripcion);

    return this.http.post(`${API_BASE_URL}/registroEmpresa`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'text'
    });
  }
}
