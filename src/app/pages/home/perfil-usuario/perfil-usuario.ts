import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { JuegoBiblioteca, ObtenerPerfilUsuarioService, UsuarioComun } from '../../../services/ver-perfil-usuario-service';

@Component({
  selector: 'app-perfil-usuario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil-usuario.html',
  styleUrl: './perfil-usuario.css'
})
export class PerfilUsuarioComponent implements OnInit {

  bibliotecaVisible = false;
  usuario!: UsuarioComun;
  biblioteca: JuegoBiblioteca[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private perfilService: ObtenerPerfilUsuarioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const mail = this.route.snapshot.paramMap.get('mail');

    if (!mail) {
      this.error = 'Usuario inválido';
      this.cargando = false;
      this.cdr.detectChanges();
      return;
    }

    this.perfilService.obtenerPerfil(mail).subscribe({
      next: (data) => {
        this.usuario = data.usuario!;

        this.bibliotecaVisible = data.visibilidad === true;
        this.biblioteca = this.bibliotecaVisible
          ? data.biblioteca ?? []
          : [];

        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar el perfil del usuario';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }
  onImgError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = 'assets/no-image.png';
    img.onerror = null;
  }
}
