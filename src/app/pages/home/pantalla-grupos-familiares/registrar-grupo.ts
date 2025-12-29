import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GruposFamiliaresService } from '../../../services/obtener-gruposFamiliares-service';
import { LoginService } from '../../../services/login-service';

@Component({
  selector: 'app-registrar-grupo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar-grupo.html',
  styleUrls: ['./registrar-grupo.css']
})
export class RegistrarGrupoComponent {

  nombreGrupo = '';
  cargando = false;
  error: string | null = null;
  exito: string | null = null;
  usuarioMail: string | null = null;

  constructor(
    private gruposService: GruposFamiliaresService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef
  ) {
    const usuario = this.loginService.user();
    this.usuarioMail = usuario?.mail ?? null;
  }

  crearGrupo(): void {
    if (!this.usuarioMail || !this.nombreGrupo.trim()) {
      this.error = 'Debe ingresar un nombre de grupo';
      return;
    }

    this.cargando = true;
    this.error = null;
    this.exito = null;

    this.gruposService.crearGrupoFamiliar(this.usuarioMail, this.nombreGrupo.trim())
      .subscribe({
        next: () => {
          this.exito = `Grupo "${this.nombreGrupo}" creado con éxito`;
          this.nombreGrupo = '';
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: err => {
          this.error = err.error || 'Error al crear grupo';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }
}
