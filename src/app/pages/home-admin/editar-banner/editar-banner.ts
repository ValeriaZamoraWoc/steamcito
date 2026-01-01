import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameService, Juego } from '../../../services/game-service';
import { BannerService } from '../../../services/banner-service';

@Component({
  selector: 'app-editar-banner',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './editar-banner.html',
  styleUrl: './editar-banner.css'
})
export class EditarBannerComponent implements OnInit {

  juegos: Juego[] = [];
  juegoSeleccionado: Juego | null = null;

  cargando = true;
  mensaje = '';
  error = '';

  constructor(
    private juegosService: GameService,
    private bannerService: BannerService
  ) {}

  ngOnInit(): void {
    this.juegosService.obtenerTodos().subscribe({
      next: data => {
        this.juegos = data;
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar juegos';
        this.cargando = false;
      }
    });
  }

  seleccionarJuego(juego: Juego): void {
    this.juegoSeleccionado = juego;
    this.mensaje = '';
    this.error = '';
  }

  guardarCambios(): void {
    if (!this.juegoSeleccionado) {
      this.error = 'Debes seleccionar un juego';
      return;
    }

    this.bannerService.editarBanner(this.juegoSeleccionado.id).subscribe({
      next: () => {
        this.mensaje = 'Banner actualizado correctamente';
        this.error = '';
      },
      error: () => {
        this.error = 'Error al actualizar el banner';
        this.mensaje = '';
      }
    });
  }
}
