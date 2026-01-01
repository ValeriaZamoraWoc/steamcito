import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {CategoriasClasificacionesService,Categoria} from '../../../../services/categorias-clasificaciones-service';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface JuegoVenta {
  nombre: string;
  totalVentas: number;
}

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './juegos-categoria.html',
  styleUrl: '../reportes-admin.css' 
})
export class JuegosCategoriaComponent implements OnInit {

  categorias: Categoria[] = [];
  categoriaSeleccionada: string = '';

  juegos: JuegoVenta[] = [];
  cargando = false;
  error: string | null = null;

  constructor(
    private catService: CategoriasClasificacionesService,
    private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef,
        private jasper : JasperReportsService
  ) {}

  ngOnInit(): void {
    this.catService.obtenerCategorias().subscribe({
      next: (data) => this.categorias = data,
      error: () => this.error = 'Error al cargar categorías'
    });
    this.cdr.detectChanges();
  }

  buscar(): void {
    if (!this.categoriaSeleccionada) return;

    this.cargando = true;
    this.error = null;
    this.cdr.detectChanges();
    this.reportesService
      .obtenerReporte('juegosMasVendidosCategoria', {
        categoria: this.categoriaSeleccionada
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
          this.error = 'Error al cargar juegos';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  exportarJasper(): void {
    if (!this.categoriaSeleccionada) return;

    this.jasper.masVendidosCategoriaAdmin(this.categoriaSeleccionada)
      .subscribe({
        next: (blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `juegos_mas_vendidos_categoria_${this.categoriaSeleccionada}.pdf`;
          a.click();
          window.URL.revokeObjectURL(url);
        },
        error: () => {
          this.error = 'Error al exportar reporte';
          this.cdr.detectChanges();
        }
      });
  }   
}

