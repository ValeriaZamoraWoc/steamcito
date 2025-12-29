import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login-service';
import { GrupoFamiliar, GruposFamiliaresService } from '../../../services/obtener-gruposFamiliares-service';
import { Router, ActivatedRoute, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-opciones-reportes',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './opciones-reportes.html',
styleUrl: './opciones-reportes.css' 
})
export class OpcionesReportesComponent implements OnInit {

  grupos: GrupoFamiliar[] = [];
  cargandoGrupos = true;
  error: string | null = null;

  mostrarPersonales = false;
  mostrarGrupos = false;

  constructor(
    private gruposService: GruposFamiliaresService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute 
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargandoGrupos = false;
      return;
    }

    this.gruposService.obtenerGruposPorUsuario(usuario.mail).subscribe({
      next: data => {
        this.grupos = data;
        this.cargandoGrupos = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar grupos familiares';
        this.cargandoGrupos = false;
      }
    });
  }

  togglePersonales() {
    this.mostrarPersonales = !this.mostrarPersonales;
  }

  toggleGrupos() {
    this.mostrarGrupos = !this.mostrarGrupos;
  }

  reportePersonal(tipo: string) {
    this.router.navigate(
      ['personales', tipo],
      { relativeTo: this.route }
    );
  }

  reporteGrupo(grupoId: number, tipo: string) {
    this.router.navigate(
      ['grupos', grupoId, tipo],
      { relativeTo: this.route }
    );
  }

  verHistorialGastos() {
  this.router.navigate(
    ['historial-gastos'],
    { relativeTo: this.route }
  );
}

}
