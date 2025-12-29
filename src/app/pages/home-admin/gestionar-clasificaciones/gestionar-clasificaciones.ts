import { ChangeDetectorRef, Component } from '@angular/core';
import { CategoriasClasificacionesService, Clasificacion } from '../../../services/categorias-clasificaciones-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
  selector: 'app-gestionar-clasificaciones',
  imports: [CommonModule, FormsModule],
  templateUrl: './gestionar-clasificaciones.html',
  styleUrl: './gestionar-clasificaciones.css',
})
export class GestionarClasificacionesComponent {

  clasificaciones: Clasificacion[] = [];
  nuevaClasificacion = '';
  cargando = true;
  error: string | null = null;

  constructor(
    private service: CategoriasClasificacionesService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarClasificaciones();
  }

  cargarClasificaciones() {
    this.service.obtenerClasificaciones().subscribe({
      next: data => {
        this.clasificaciones = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudieron cargar las clasificaciones';
        this.cargando = false;
      }
    });
  }

  crearClasificacion() {
  if (!this.nuevaClasificacion.trim()) return;

  this.service.agregarClasificacion(this.nuevaClasificacion).subscribe({
    next: () => {},
    error: () => {},
    complete: () => {
      this.nuevaClasificacion = '';
      this.cargarClasificaciones();
    }
  });
}

  eliminarClasificacion(id: number) {
    this.service.eliminarClasificacion(id).subscribe({
      next: () => this.cargarClasificaciones()
    });
  }
}