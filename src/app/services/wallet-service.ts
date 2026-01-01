import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface WalletResponse {
  exito: boolean;
  wallet?: Wallet;
  mensaje?: string;
}

export interface Wallet {
  mail: string;
  saldo: number;
}

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  constructor(private http: HttpClient) {}

  obtenerEstadoWallet(mail: string): Observable<WalletResponse> {
    return this.http.get<WalletResponse>(
      `${API_BASE_URL}/obtenerEstadoWallet`,
      { params: { mail } }
    );
  }

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
