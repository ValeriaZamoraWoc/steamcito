import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../services/user-service';
import { GrupoFamiliar, GrupoFamiliarService } from '../../../services/grupo-familiar-service';
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
    private gruposService: GrupoFamiliarService,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute 
  ) {}

  ngOnInit(): void {
    const usuario = this.userService.user();

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
