import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../../services/login-service';
import {
  ReportesEmpresaService,
  VentaEmpresa,
  ReporteEmpresaResponse
} from '../../../../services/reportes-empresa-service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ventas.html',
   styleUrl: '../reportes-dev.css' 
})
export class ReporteVentasDevComponent implements OnInit {

  ventas: VentaEmpresa[] = [];
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
      .obtenerReporte<VentaEmpresa[]>(idEmpresa, 'ventas')
      .subscribe({
        next: resp => {
          this.ventas = resp.datos;
          this.cargando = false;
        },
        error: () => {
          this.error = 'Error al cargar ventas';
          this.cargando = false;
        }
      });
  }
}


