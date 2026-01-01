import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../services/user-service';
import { GrupoFamiliar, GrupoFamiliarService } from '../../../services/grupo-familiar-service';
import { Router } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { RegistrarGrupoComponent } from "./registrar-grupo";

@Component({
  selector: 'app-grupos-familiares',
  standalone: true,
  imports: [CommonModule, RegistrarGrupoComponent],
  templateUrl: './pantalla-grupos-familiares.html',
  styleUrl: './pantalla-grupos-familiares.css'
})
export class GruposFamiliaresComponent implements OnInit {
  mostrandoRegistro = false;
  grupos: GrupoFamiliar[] = [];
  cargando = true;
  error: string | null = null;
  usuarioMail: string | null = null;

  constructor(
    private gruposService: GrupoFamiliarService,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    const usuario = this.userService.user();
    this.usuarioMail = usuario?.mail ?? null;

    const grupoDesdeBuscador = window.history.state?.grupoEncontrado;
    if (grupoDesdeBuscador) {
      const id = grupoDesdeBuscador.idGrupoFamiliar || grupoDesdeBuscador.id;
      this.gruposService.obtenerIntegrantes(id).subscribe(integrantes => {
        this.grupos = [{
          idGrupoFamiliar: id,
          nombreGrupo: grupoDesdeBuscador.nombreGrupo || 'Sin nombre',
          integrantes
        }];
        this.cargando = false;
        this.cdr.detectChanges();
      });
    } else {
      this.cargarGrupos();
    }
  }

  cargarGrupos() {
    this.gruposService.obtenerGruposPorUsuario(this.usuarioMail!).subscribe({
      next: gruposSimplificados => {
        if (!gruposSimplificados?.length) {
          this.grupos = [];
          this.cargando = false;
          return;
        }

        const observables = gruposSimplificados.map(grupo => {
          const nombre = grupo.nombreGrupo || 'Sin nombre';
          return this.gruposService.obtenerIntegrantes(grupo.idGrupoFamiliar).pipe(
            map(integrantes => ({
              idGrupoFamiliar: grupo.idGrupoFamiliar,
              nombreGrupo: nombre,
              integrantes: integrantes || []
            }))
          );
        });

        forkJoin(observables).subscribe({
          next: gruposCompletos => {
            this.grupos = gruposCompletos;
            this.cargando = false;
            this.cdr.detectChanges();
          },
          error: () => {
            this.error = 'Error al cargar integrantes';
            this.cargando = false;
          }
        });
      },
      error: () => {
        this.error = 'Error al cargar grupos';
        this.cargando = false;
      }
    });
  }

  verIntegrantes(grupo: GrupoFamiliar) {
    this.router.navigate(['/app/grupos-familiares', grupo.idGrupoFamiliar]);
  }

  estaEnGrupo(grupo: GrupoFamiliar): boolean {
    if (!this.usuarioMail || !grupo.integrantes?.length) return false;

    const mailUsuario = this.usuarioMail.toLowerCase().trim();
    return grupo.integrantes.some(u => u.mail?.toLowerCase().trim() === mailUsuario);
  }

  unirseAlGrupo(grupo: GrupoFamiliar) {
    if (!this.usuarioMail) return;
    console.log('Intentando unirse al grupo ID:', grupo.idGrupoFamiliar, 'Usuario:', this.usuarioMail);

    this.gruposService.registrarUsuarioEnGrupo(this.usuarioMail, grupo.idGrupoFamiliar).subscribe({
      next: () => this.cargarGrupos(),
      error: err => this.error = err.error || 'Error al unirse'
    });
  }

  salirDelGrupo(grupo: GrupoFamiliar) {
    if (!this.usuarioMail) return;
    this.gruposService.sacarUsuarioDelGrupo(grupo.idGrupoFamiliar, this.usuarioMail).subscribe({
      next: () => this.cargarGrupos(),
      error: err => this.error = err.error || 'Error al salir'
    });
  }
}
