import { Component, OnInit ,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  CategoriasClasificacionesService,
  Categoria
} from '../../../../services/categorias-clasificaciones-service';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';

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
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.catService.obtenerCategorias().subscribe({
      next: data => this.categorias = data,
      error: () => this.error = 'Error al cargar categorías'
    });
    this.cdr.detectChanges();
  }

  buscar(): void {
    if (!this.categoriaSeleccionada) return;

    this.cargando = true;
    this.error = null;

    this.reportesService.obtenerReporte(
      'mejoresCalificadosCategoria',
      { categoria: this.categoriaSeleccionada }
    ).subscribe({
      next: resp => {
        this.juegos = resp.datos.map((fila: string[]) => ({
          nombre: fila[1],
          promedio: Number(fila[3].replace(',', '.'))
        }));
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar reporte';
        this.cargando = false;
      }
    });
    this.cdr.detectChanges();
  }
}
