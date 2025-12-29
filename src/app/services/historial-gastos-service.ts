import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class HistorialGastosService {

  constructor(private http: HttpClient) {}

  obtenerHistorial(mail: string): Observable<string[]> {
    return this.http.get<string[]>(
      `${API_BASE_URL}/obtenerHistorialGastos`,
      { params: { mail } }
    );
  }
}
