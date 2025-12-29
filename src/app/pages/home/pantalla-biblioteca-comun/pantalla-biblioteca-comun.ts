import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login-service';
import { VisibilidadBibliotecaService } from '../../../services/cambiar-visibilidad-service';
import { BibliotecaUsuarioService } from '../../../services/biblioteca-usuario-service';
import { JuegoBiblioteca } from '../../../services/ver-perfil-usuario-service';

@Component({
  selector: 'app-pantalla-biblioteca-comun',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pantalla-biblioteca-comun.html',
  styleUrl: './pantalla-biblioteca-comun.css',
})
export class PantallaBibliotecaComun implements OnInit {

  visibilidadBiblioteca = false;
  cambiandoVisibilidad = false;
  mensaje: string | null = null;
  juegos: JuegoBiblioteca[] = [];
  cargando = true;

  constructor(
    private bibliotecaService: BibliotecaUsuarioService,
    private loginService: LoginService,
    private visibilidadService: VisibilidadBibliotecaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      console.warn('Usuario no autenticado');
      this.cargando = false;
      return;
    }

    this.bibliotecaService.obtenerBiblioteca(usuario.mail).subscribe({
      next: resp => {
        this.juegos = resp.biblioteca;
        this.visibilidadBiblioteca = resp.visibilidad;
        this.cargando = false;
        this.cdr.detectChanges();
        console.log('Biblioteca:', resp);
      },
      error: err => {
        console.error('Error al cargar biblioteca', err);
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  trackById(index: number, juego: JuegoBiblioteca) {
    return juego.id;
  }

  onImgError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = 'assets/no-image.png';
    img.onerror = null;
  }
  estaInstalado(juego: JuegoBiblioteca): boolean {
    return juego.instalado;
  }
  cambiarVisibilidad() {
      const usuario = this.loginService.user();

      if (!usuario?.mail) {
        return;
      }

      this.cambiandoVisibilidad = true;
      this.mensaje = null;

      this.visibilidadService.cambiarVisibilidad(usuario.mail).subscribe({
        next: msg => {
          this.visibilidadBiblioteca = !this.visibilidadBiblioteca;
          this.mensaje = msg;
          this.cambiandoVisibilidad = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.mensaje = 'No se pudo cambiar la visibilidad de la biblioteca';
          this.cambiandoVisibilidad = false;
          this.cdr.detectChanges();
        }
      });
    }
}
