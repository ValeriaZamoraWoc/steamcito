import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface ReporteAdminResponse {
  exito: boolean;
  tipo: string;
  total: number;
  datos: string[][];
}

@Injectable({
  providedIn: 'root'
})
export class ReportesAdminService {

  constructor(private http: HttpClient) {}

  obtenerReporte(
    tipo: string,
    paramsExtra?: { [key: string]: string }
  ) {
    let params: any = { tipo };

    if (paramsExtra) {
      params = { ...params, ...paramsExtra };
    }

    return this.http.get<any>(
      `${API_BASE_URL}/reportesAdmin`,
      { params }
    );
  }
}
