import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class AgregarSaldoWalletService {

  constructor(private http: HttpClient) {}

  agregarSaldo(mail: string, monto: number): Observable<void> {
    return this.http.post<void>(
      `${API_BASE_URL}/agregarSaldo`,
      null,
      {
        params: {
          mail,
          monto
        }
      }
    );
  }
}
