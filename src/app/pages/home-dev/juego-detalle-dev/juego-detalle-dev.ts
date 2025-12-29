import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../../../services/login-service';
import { ObtenerJuegoPorIdService } from '../../../services/obtener-juego-por-id-service';
import { JuegoEmpresaPerfil } from '../../../services/ver-perfil-empresa-service';
import { CommonModule } from '@angular/common';
import { Comentario } from '../../../services/obtener-comentarios-por-juego-service';
import { ObtenerComentariosPorJuegoService } from '../../../services/obtener-comentarios-por-juego-service';
import { ComentarioComponent } from '../../home/juego-detalle/comentario.component';

@Component({
  selector: 'app-juego-detalle-dev',
  imports: [CommonModule,ComentarioComponent],
  templateUrl: './juego-detalle-dev.html',
  styleUrl: './juego-detalle-dev.css',
})
export class JuegoDetalleDevComponent implements OnInit {
  comentarios: Comentario[] = [];
  comentariosRaiz: Comentario[] = [];
  juego!: JuegoEmpresaPerfil;
  idEmpresa!: number;

  constructor(
    private route: ActivatedRoute,
    private cargaComentariosService: ObtenerComentariosPorJuegoService,
    private juegosService: ObtenerJuegoPorIdService,
    private loginService: LoginService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id') || '0');
    this.idEmpresa = this.loginService.getIdEmpresa() ?? 0;

    this.juegosService.obtenerJuegoPorId(id).subscribe(j => {
      this.juego = j;
      this.cargarComentarios(id); 
      this.cdr.detectChanges();
    });
  }

  cargarComentarios(idJuego: number): void {
    this.cargaComentariosService.obtenerComentarios(idJuego).subscribe({
      next: lista => {
        this.comentarios = lista;
        this.comentariosRaiz = this.construirArbolComentarios(lista);
        this.cdr.detectChanges();
      },
      error: () => {
        console.error('Error al cargar comentarios');
      }
    });
  }

  construirArbolComentarios(comentarios: Comentario[]): Comentario[] {
    const mapa = new Map<number, Comentario>();

    comentarios.forEach(c => {
      c.respuestas = [];
      mapa.set(c.idComentario, c);
    });

    const raiz: Comentario[] = [];

    comentarios.forEach(c => {
      if (c.idComentrioPadre === 0) {
        raiz.push(c);
      } else {
        const padre = mapa.get(c.idComentrioPadre);
        if (padre) {
          padre.respuestas!.push(c);
        }
      }
    });

    return raiz;
  }
  editarJuego() {
    if (this.juego && this.juego.id) {
    this.router.navigate(['/app/home-dev/editar-juego', this.juego.id]);
  } else {
    console.error("No se puede editar: El juego no tiene ID cargado.");
  }
    this.cdr.detectChanges();
  }
  suspenderVenta() {
    const idEmpresa = this.loginService.getIdEmpresa();
    if (!idEmpresa) return;

    this.juegosService
      .suspenderVenta(this.juego.nombreJuego, idEmpresa.toString())
      .subscribe(() => {
        this.juego.enVenta = false;
        this.cdr.detectChanges(); 
      });
  }
}

