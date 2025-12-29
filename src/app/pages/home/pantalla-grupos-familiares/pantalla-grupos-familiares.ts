import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login-service';
import { GrupoFamiliar, GruposFamiliaresService } from '../../../services/obtener-gruposFamiliares-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-grupos-familiares',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pantalla-grupos-familiares.html',
  styleUrl: './pantalla-grupos-familiares.css'
})
export class GruposFamiliaresComponent implements OnInit {

  grupos: GrupoFamiliar[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private gruposService: GruposFamiliaresService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      this.cdr.detectChanges();
      return;
    }

    this.gruposService.obtenerGruposPorUsuario(usuario.mail).subscribe({
      next: data => {
        this.grupos = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar grupos familiares';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  verIntegrantes(grupo: GrupoFamiliar) {
    this.router.navigate([
      '/app/grupos-familiares',
      grupo.idGrupoFamiliar
    ]);
  }
}
