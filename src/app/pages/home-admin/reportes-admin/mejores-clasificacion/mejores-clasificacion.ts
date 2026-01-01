import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  CategoriasClasificacionesService,
  Clasificacion
} from '../../../../services/categorias-clasificaciones-service';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface JuegoCalificado {
  nombre: string;
  promedio: number;
}

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mejores-clasificacion.html',
  styleUrl: '../reportes-admin.css' 
})
export class MejoresClasificacionComponent implements OnInit {

  clasificaciones: Clasificacion[] = [];
  clasificacionSeleccionada = '';

  juegos: JuegoCalificado[] = [];
  cargando = false;
  error: string | null = null;

  constructor(
    private clasifService: CategoriasClasificacionesService,
    private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef,
        private jasper : JasperReportsService
  ) {}

  ngOnInit(): void {
    this.clasifService.obtenerClasificaciones().subscribe({
      next: data => this.clasificaciones = data,
      error: () => this.error = 'Error al cargar clasificaciones'
    });
    this.cdr.detectChanges();
  }

  buscar(): void {
    const clasificacion = this.clasificacionSeleccionada?.trim();
    if (!clasificacion) return; 

    this.cargando = true;
    this.error = null;

    this.reportesService.obtenerReporte(
      'mejoresCalificadosClasificacion',
      { clasificacion }
    ).subscribe({
      next: resp => {
        this.juegos = resp.datos.map((fila: string[]) => ({
          nombre: fila[1],
          promedio: Number(fila[3].replace(',', '.'))
        }));
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar reporte';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }
  exportarJasper(): void {
    const clasificacion = this.clasificacionSeleccionada?.trim();
    if (!clasificacion) return; 
    this.jasper.mejorCalificadosClasificacionAdmin(clasificacion).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'mejores_calificados_clasificacion.pdf';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error al exportar el reporte:', error);
      }
    });
  }
}