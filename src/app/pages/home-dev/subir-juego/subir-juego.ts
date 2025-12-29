import { Component, OnInit , ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../../services/login-service';
import { RegistrarJuegoService } from '../../../services/registrar-juego-service';
import { Categoria, CategoriasClasificacionesService, Clasificacion } from '../../../services/categorias-clasificaciones-service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { ObtenerJuegoPorIdService } from '../../../services/obtener-juego-por-id-service';
import { EmpresaPerfil, VerPerfilEmpresaService } from '../../../services/ver-perfil-empresa-service';

@Component({
  selector: 'app-subir-juego',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subir-juego.html',
  styleUrl: './subir-juego.css'
})
export class SubirJuegoComponent implements OnInit {
  
  empresaPerfil: EmpresaPerfil | null = null;
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
  idJuegoCreado: number | null = null;
  archivoImagen: File | null = null;
  imagenSubida = false;

  constructor(
    private registrarJuegoService: RegistrarJuegoService,
    private loginService: LoginService,
    private catClasService: CategoriasClasificacionesService,
    private cdr: ChangeDetectorRef,
    private juegosService: ObtenerJuegoPorIdService,
    private router: Router,
    private perfilEmpresaService: VerPerfilEmpresaService
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
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'Empresa no identificada';
      return;
    }

    this.perfilEmpresaService.obtenerPerfilEmpresa(idEmpresa)
      .subscribe({
        next: (resp: any) => {
          this.empresaPerfil = resp.empresa ?? resp;
        },
        error: () => {
          this.error = 'No se pudo cargar el perfil de la empresa';
        }
      });
          this.cdr.detectChanges();
  }

  registrar() {
    this.error = '';
    this.mensaje = '';
    if (!this.empresaPerfil) {
        this.error = 'Perfil de empresa no cargado';
        return;
      }

    const nombreEmpresa = this.empresaPerfil.nombreEmpresa;

    this.registrarJuegoService.registrarJuego({
      nombre: this.nombre,
      descripcion: this.descripcion,
      especificaciones: this.especificaciones,
      clasificacion: this.clasificacion.toString(),
      categoria: this.categoria.toString(),
      empresa: nombreEmpresa,
      precio: this.precio!,
      fechaLanzamiento: this.fechaLanzamiento
    }).subscribe({
      next: resp => {
        this.idJuegoCreado = resp.idJuego;
        this.mensaje = resp.mensaje;
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
  onFileChange(e: any) {
    this.archivoImagen = e.target.files[0];
  }
  subirImagen() {
    if (!this.archivoImagen || !this.idJuegoCreado) return;

    const form = new FormData();
    form.append('idJuego', this.idJuegoCreado.toString());
    form.append('imagen', this.archivoImagen);

    this.juegosService.subirImagenJuego(form)
      .subscribe({
        next: () => {
          this.imagenSubida = true;
          this.mensaje = 'Imagen subida. Juego listo para publicar.';
        },
        error: () => {
          this.error = 'Error al subir la imagen';
        }
      });
          this.cdr.detectChanges();

  }
  volverAlCatalogo() {
    if (!this.imagenSubida) return;

    this.limpiarFormulario();
    this.router.navigate(['/app/home-dev/catalogo']);
        this.cdr.detectChanges();

  }
}
