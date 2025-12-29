import { Component,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login-service';
import { BuscadorService } from '../../services/buscador-service';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-buscador',
  imports: [CommonModule, FormsModule,RouterLink],
  templateUrl: './buscador.html',
  styleUrl: './buscador.css'
})
export class BuscadorComponent {

  tipo = 'juego';
  query = '';
  resultados: any[] = [];
  error: string | null = null;

  constructor(
    private buscadorService: BuscadorService,
    private router: Router,
    public loginService: LoginService,
    private cdr: ChangeDetectorRef
  ) {}

  buscar() {
    this.error = null;
    this.resultados = [];

    this.buscadorService.buscar(this.tipo, this.query).subscribe({
      next: (resp: any) => {

        if (this.tipo === 'juego') {
          const juego = resp;
          this.irAJuego(juego.id);
          return;
        }

        if (this.tipo === 'empresa') {
          this.router.navigate([
            '/app/home-dev/ver-perfil-empresa',
            resp.idEmpresa
          ]);
          return;
        }

        if (this.tipo === 'usuariomail' || this.tipo === 'usuarionick') {
          this.router.navigate(['/app/perfil-usuario', resp.mail]);
          return;
        }

        if (this.tipo === 'grupofamiliar') {
          this.router.navigate([
            '/app/grupos-familiares',
            resp.idGrupoFamiliar
          ]);
          return;
        }

        this.resultados = Array.isArray(resp) ? resp : [resp];
      },
      error: () => {
        this.error = 'No se encontraron resultados';
      }
    });
    this.cdr.detectChanges();
  }
  ngOnInit() {
    if (this.esDesarrollador && ['empresa','usuariomail','usuarionick','grupofamiliar'].includes(this.tipo)) {
      this.tipo = 'juego';
    }
  }
  private irAJuego(id: number) {
    if (this.loginService.isDesarrollador()) {
      this.router.navigate(['/app/home-dev/juego', id]);
    } if (this.loginService.isAdmin()) {
      this.router.navigate(['/app/admin/juego', id]);
    } else {
      this.router.navigate(['/app/juego', id]);
    }
    this.cdr.detectChanges();
  }

    get esDesarrollador(): boolean {
    return this.loginService.isDesarrollador();
  }

  get puedeBuscarSocial(): boolean {
    return !this.esDesarrollador;
  }

  get puedeBuscarEmpresa(): boolean {
    return !this.esDesarrollador;
  }

}

