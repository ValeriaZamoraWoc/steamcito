import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../../services/game-service';
import { CategoriasClasificacionesService, Categoria, Clasificacion } from '../../../services/categorias-clasificaciones-service';

@Component({
  selector: 'app-editar-juego',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-juego.html',
  styleUrl: './editar-juego.css'
})
export class EditarJuegoComponent implements OnInit {

  idJuego!: number;

  nombre = '';
  descripcion = '';
  especificaciones = '';
  precio!: number;
  fechaLanzamiento = '';
  categoria!: number;
  clasificacion!: number;

  categorias: Categoria[] = [];
  clasificaciones: Clasificacion[] = [];

  archivoImagen: File | null = null;

  mensaje = '';
  error = '';

  constructor(
    private route: ActivatedRoute,
    private juegosService: GameService,
    private catClasService: CategoriasClasificacionesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.idJuego = Number(this.route.snapshot.paramMap.get('id'));

    // cargar juego
    this.juegosService.obtenerJuego(this.idJuego).subscribe((juego: any) => {
      this.nombre = juego.nombreJuego;
      this.descripcion = juego.descripcion;
      this.especificaciones = juego.especificaciones;
      this.precio = juego.precio;
      this.categoria = juego.categoria;
      this.clasificacion = juego.clasificacion;
    });

    // combos
    this.catClasService.obtenerCategorias()
      .subscribe(c => this.categorias = c);

    this.catClasService.obtenerClasificaciones()
      .subscribe(c => this.clasificaciones = c);
  }
  guardarCambios() {
    this.juegosService.actualizarJuego({
      idJuego: this.idJuego,
      nombreJuego: this.nombre,
      descripcion: this.descripcion,
      especificaciones: this.especificaciones,
      clasificacion: this.clasificacion,
      categoria: this.categoria,
      precio: this.precio,
      fechaLanzamiento: this.fechaLanzamiento
    }).subscribe({
      next: () => {
        this.mensaje = 'Juego actualizado correctamente';

        if (this.archivoImagen) {
          this.subirImagen();
        }
      },
      error: err => {
        console.error(err);
        this.error = 'Error al actualizar el juego';
      }
    });
  }
  onFileChange(e: any) {
    this.archivoImagen = e.target.files[0];
  }

  subirImagen() {
    if (!this.archivoImagen) return;

    const form = new FormData();
    form.append('idJuego', this.idJuego.toString());
    form.append('imagen', this.archivoImagen);

    this.juegosService.subirImagenJuego(form).subscribe();
  }

  volver() {
    this.router.navigate(['/app/home-dev/catalogo']);
  }
}
