import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { GameService, Juego } from '../../../services/game-service';
import { CategoriasClasificacionesService, Clasificacion } from '../../../services/categorias-clasificaciones-service';

@Component({
  selector: 'app-juego-detalle-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './juego-detalle-admin.html',
  styleUrl: './juego-detalle-admin.css'
})
export class JuegoDetalleAdminComponent implements OnInit {

  juego: Juego | null = null;
  clasificaciones: Clasificacion[] = [];
  clasificacionSeleccionada!: number;

  cargando = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private juegoService: GameService,
        private cdr: ChangeDetectorRef,
    private clasificacionesService: CategoriasClasificacionesService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!id) {
      this.error = 'ID inválido';
      this.cargando = false;
      return;
    }
      this.cargarClasificaciones();
    this.juegoService.obtenerJuego(id).subscribe({
      next: juego => {
        this.juego = juego;
        this.clasificacionSeleccionada = juego.clasificacion;
        this.cargando = false;

        this.cargarClasificaciones();
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudo cargar el juego';
        this.cargando = false;
      }
    });
        this.cdr.detectChanges();
  }

  cargarClasificaciones(): void {
    this.clasificacionesService.obtenerClasificaciones()
      .subscribe(lista => {
        this.clasificaciones = lista;
      });
          this.cdr.detectChanges();

  }

  cambiarClasificacion(): void {
    if (!this.juego) return;

    this.clasificacionesService
      .cambiarClasificacionJuego(
        this.juego.id,
        this.clasificacionSeleccionada
      )
      .subscribe({
        next: () => alert('Clasificación actualizada correctamente'),
        error: () => alert('Error al cambiar la clasificación')
      });
          this.cdr.detectChanges();

  }
  onImgError(event: any) {
  const placeholder = 'assets/no-image.png'; 
    if (event.target.src !== placeholder) {
    event.target.src = placeholder;
  }
}
}
