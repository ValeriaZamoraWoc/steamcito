import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClasificacionFavorita, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { LoginService } from '../../../../services/login-service';

@Component({
  selector: 'app-clasificaciones-favoritas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './clasificaciones-favoritas.html',
styleUrl: '../opciones-reportes.css' 
})
export class ClasificacionesFavoritasComponent implements OnInit {

  clasificaciones: ClasificacionFavorita[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private reportesService: ReportesUsuarioComunService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<ClasificacionFavorita[]>(
        usuario.mail,
        'clasificacionesFavoritas'
      )
      .subscribe({
        next: resp => {
          this.clasificaciones = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudieron cargar tus clasificaciones favoritas';
          this.cargando = false;
        }
      });
  }
}
