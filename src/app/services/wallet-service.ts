import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';
import { Injectable } from '@angular/core';

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
export class ObtenerEstadoWalletService {

  constructor(private http: HttpClient) {}

  obtenerEstadoWallet(mail: string): Observable<WalletResponse> {
    return this.http.get<WalletResponse>(
      `${API_BASE_URL}/obtenerEstadoWallet`,
      { params: { mail } }
    );
  }
}
