import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class VisibilidadBibliotecaService {

  constructor(private http: HttpClient) {}

  cambiarVisibilidad(mail: string): Observable<string> {
    const params = new HttpParams().set('mail', mail);

    return this.http.post(
      `${API_BASE_URL}/cambiarVisibilidadBiblioteca`,
      null,
      { params, responseType: 'text' }
    );
  }
}
