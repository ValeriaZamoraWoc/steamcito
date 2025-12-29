import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { API_BASE_URL } from '../core/api.config';
import { Observable } from 'rxjs';

export interface PeticionComision {
  idEmpresa: number;
  empresa: string;
  porcentaje: number;
  estado: string;
}

@Injectable({
  providedIn: 'root'
})
export class PeticionComisionService {

  constructor(private http: HttpClient) {}

  registrarPeticion(data: {
    empresa: number;
    porcentaje: number;
  }): Observable<string> {

    const body = new URLSearchParams();
    body.set('empresa', data.empresa.toString());
    body.set('porcentaje', data.porcentaje.toString());

    return this.http.post(
      `${API_BASE_URL}/registroPeticionComision`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        responseType: 'text'
      }
    );
  }
  obtenerPeticiones(): Observable<PeticionComision[]> {
    return this.http.get<PeticionComision[]>(
      `${API_BASE_URL}/obtenerPeticionesComisiones`
    );
  }
  aceptarPeticion(idEmpresa: number): Observable<any> {
    const params = new HttpParams().set('idEmpresa', idEmpresa);
    return this.http.post(
      `${API_BASE_URL}/aceptarPeticionComision`,
      params
    );
  }

  rechazarPeticion(idEmpresa: number): Observable<any> {
    const params = new HttpParams().set('idEmpresa', idEmpresa);
    return this.http.post(
      `${API_BASE_URL}/rechazarPeticionComision`,
      params
    );
  }
}
