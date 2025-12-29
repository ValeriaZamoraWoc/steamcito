import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../core/api.config';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistroEmpresaService {

  constructor(private http: HttpClient) {}

  async registrarEmpresa(nombre: string, descripcion: string): Promise<void> {
    const body = new URLSearchParams();
    body.set('nombre', nombre);
    body.set('descripcion', descripcion);

    await firstValueFrom(
      this.http.post(
        `${API_BASE_URL}/registroEmpresa`,
        body.toString(),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
      )
    );
  }

}
