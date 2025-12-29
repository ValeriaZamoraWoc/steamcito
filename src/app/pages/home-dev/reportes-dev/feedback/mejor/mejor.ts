import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../../../services/login-service';
import {
  ReportesEmpresaService,
  JuegoCalificado,
  ReporteEmpresaResponse
} from '../../../../../services/reportes-empresa-service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mejor.html',
  styleUrl: '../../reportes-dev.css'
})
export class ReporteMejorCalificadosDevComponent implements OnInit {

  juegos: JuegoCalificado[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private loginService: LoginService,
    private reportesService: ReportesEmpresaService
  ) {}

  ngOnInit(): void {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<JuegoCalificado[]>(idEmpresa, 'mejorcalificados')
      .subscribe({
        next: (resp: ReporteEmpresaResponse<JuegoCalificado[]>) => {
          this.juegos = resp.datos;
          this.cargando = false;
        },
        error: () => {
          this.error = 'Error al cargar feedback positivo';
          this.cargando = false;
        }
      });
  }
}
