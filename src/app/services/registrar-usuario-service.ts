import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../core/api.config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistroUsuariosService {

  constructor(private http: HttpClient) {}

  registrarDesarrolladorEmpresa(data: {
    mail: string;
    nickname: string;
    contrasena: string;
    fechaNacimiento: string;
    empresa: number;
  }): Observable<string> {

    const body = new URLSearchParams();
    body.set('tipoUsuario', 'Desarrollador');
    body.set('mail', data.mail);
    body.set('nickname', data.nickname);
    body.set('contrasena', data.contrasena);
    body.set('fechaNacimiento', data.fechaNacimiento);
    body.set('idEmpresa', data.empresa.toString());

    return this.http.post(
      `${API_BASE_URL}/registrarUsuarios`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        responseType: 'text'
      }
    );
  }
}
