import { Component, OnInit , ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../../services/login-service';
import { RegistrarJuegoService } from '../../../services/registrar-juego-service';
import { Categoria, CategoriasClasificacionesService, Clasificacion } from '../../../services/categorias-clasificaciones-service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-subir-juego',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subir-juego.html',
  styleUrl: './subir-juego.css'
})
export class SubirJuegoComponent implements OnInit {

  nombre = '';
  descripcion = '';
  especificaciones = '';

  clasificacion!: number;
  categoria!: number;

  precio: number | null = null;
  fechaLanzamiento = '';

  categorias: Categoria[] = [];
  clasificaciones: Clasificacion[] = [];

  mensaje = '';
  error = '';

  constructor(
    private registrarJuegoService: RegistrarJuegoService,
    private loginService: LoginService,
    private catClasService: CategoriasClasificacionesService,
    private cdr: ChangeDetectorRef,
      private router: Router
  ) {}

  ngOnInit(): void {
    this.router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe(() => {
      this.limpiarFormulario();
    });
    this.catClasService.obtenerCategorias().subscribe({
      next: data => this.categorias = data,
      error: () => this.error = 'Error al cargar categorías'
    });

    this.catClasService.obtenerClasificaciones().subscribe({
      next: data => this.clasificaciones = data,
      error: () => this.error = 'Error al cargar clasificaciones'
      
    });
    this.cdr.detectChanges();
  }

  registrar() {
    this.mensaje = '';
    this.error = '';

    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo identificar la empresa';
      return;
    }

    if (
      !this.nombre || !this.descripcion || !this.especificaciones ||
      !this.categoria || !this.clasificacion ||
      this.precio === null || !this.fechaLanzamiento
    ) {
      this.error = 'Todos los campos son obligatorios';
      return;
    }

    this.registrarJuegoService.registrarJuego({
      nombre: this.nombre,
      descripcion: this.descripcion,
      especificaciones: this.especificaciones,
      clasificacion: this.clasificacion.toString(),
      categoria: this.categoria.toString(),
      empresa: idEmpresa,
      precio: this.precio,
      fechaLanzamiento: this.fechaLanzamiento
    }).subscribe({
      next: msg => {
        this.mensaje = msg;
        this.limpiarFormulario();
      },
      error: err => {
        this.error = err.error || 'Error al registrar el juego';
      }
    });
    this.cdr.detectChanges();
  }

  limpiarFormulario() {
    this.nombre = '';
    this.descripcion = '';
    this.especificaciones = '';
    this.clasificacion = 0;
    this.categoria = 0;
    this.precio = null;
    this.fechaLanzamiento = '';
  }
}
