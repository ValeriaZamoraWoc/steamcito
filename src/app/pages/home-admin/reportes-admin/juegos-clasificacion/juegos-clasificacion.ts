import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {CategoriasClasificacionesService,Clasificacion} from '../../../../services/categorias-clasificaciones-service';

import { ReportesAdminService } from '../../../../services/reportes-admin-service';

interface JuegoVenta {
  nombre: string;
  totalVentas: number;
}

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './juegos-clasificacion.html',
  styleUrl: '../reportes-admin.css' 
})
export class JuegosClasificacionComponent implements OnInit {

  clasificaciones: Clasificacion[] = [];
  clasificacionSeleccionada: string = '';

  juegos: JuegoVenta[] = [];
  cargando = false;
  error: string | null = null;
  
  constructor(
    private clasificacionService: CategoriasClasificacionesService,
    private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.clasificacionService.obtenerClasificaciones().subscribe({
      next: (data) => {
        this.clasificaciones = data;
      },
      error: () => {
        this.error = 'Error al cargar clasificaciones';
      }
    });
    this.cdr.detectChanges();
  }

  buscar(): void {
    if (!this.clasificacionSeleccionada) {
      this.error = 'Seleccione una clasificación';
      return;
    }

    this.cargando = true;
    this.error = null;
    this.juegos = [];
    this.cdr.detectChanges();

    this.reportesService
      .obtenerReporte('juegosMasVendidosClasificacion', {
        clasificacion: this.clasificacionSeleccionada
      })
      .subscribe({
        next: (resp) => {
          this.juegos = resp.datos.map((fila: string[]) => ({
            nombre: fila[1],
            totalVentas: Number(fila[3])
          }));
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Error al cargar juegos por clasificación';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }
}
