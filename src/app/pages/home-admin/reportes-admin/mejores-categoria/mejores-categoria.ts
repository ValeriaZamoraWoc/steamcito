import { Component, OnInit ,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  CategoriasClasificacionesService,
  Categoria
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
  templateUrl: './mejores-categoria.html',
  styleUrl: '../reportes-admin.css' 
})
export class MejoresCategoriaComponent implements OnInit {

  categorias: Categoria[] = [];
  categoriaSeleccionada = '';

  juegos: JuegoCalificado[] = [];
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
      next: data => this.categorias = data,
      error: () => this.error = 'Error al cargar categorías'
    });
    this.cdr.detectChanges();
  }

  buscar(): void {
    const categoria = this.categoriaSeleccionada?.trim();
    if (!categoria) return;

    this.cargando = true;
    this.error = null;

    this.reportesService.obtenerReporte(
      'mejoresCalificadosCategoria',
      { categoria }
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
    const categoria = this.categoriaSeleccionada?.trim();
    if (!categoria) return;

    this.jasper.mejorCalificadosCategoriaAdmin(categoria).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `mejores_calificados_categoria_${categoria}.pdf`;
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
