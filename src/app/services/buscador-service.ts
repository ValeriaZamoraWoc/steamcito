import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class BuscadorService {

  constructor(private http: HttpClient) {}

  buscar(tipo: string, query: string): Observable<any> {
    const params = new HttpParams()
      .set('tipo', tipo)
      .set('query', query);

    return this.http.get<any>(
      `${API_BASE_URL}/buscar`,
      { params }
    );
  }
}
