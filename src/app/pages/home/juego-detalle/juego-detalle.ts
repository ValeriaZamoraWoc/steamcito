import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Juego, GameService } from '../../../services/game-service';
import { Comentario, ObtenerComentariosPorJuegoService } from '../../../services/obtener-comentarios-por-juego-service';
import { ComentarioComponent } from './comentario.component';
import { BibliotecaUsuarioService } from '../../../services/biblioteca-usuario-service';
import { UserService } from '../../../services/user-service';
import { FormsModule } from '@angular/forms';
import { ComentariosJuegoService } from '../../../services/comentar-juego-service';
import { PrestamosService, Prestamo } from '../../../services/prestamos-service';
import { JuegoBiblioteca } from '../../../services/user-service';
import {CategoriasClasificacionesService,Clasificacion,Categoria} from '../../../services/categorias-clasificaciones-service';

@Component({
  selector: 'app-juego-detalle',
  standalone: true,
  imports: [CommonModule,ComentarioComponent,FormsModule],
  templateUrl: './juego-detalle.html',
  styleUrl: './juego-detalle.css'
})
export class JuegoDetalleComponent implements OnInit {
  tienePrestamoActivo = false;
  gruposDisponibles: string[] = [];
  mostrandoPrestamo = false;
  mensajePrestamo: string | null = null;
  instalado = false;
  comprado = false;
  comentarios: Comentario[] = [];
  comentariosRaiz: Comentario[] = [];
  juego: Juego | null = null;
  cargando = true;
  error: string | null = null;
  clasificaciones: Clasificacion[] = [];
  categorias: Categoria[] = [];

  nombreClasificacion = '';
  nombreCategoria = '';

constructor(
  private route: ActivatedRoute,
  private juegoService: GameService,
  private comentariosService: ObtenerComentariosPorJuegoService,
  private comentariosJuegosService: ComentariosJuegoService,
  private bibliotecaService: BibliotecaUsuarioService,
  private loginService: UserService,
  private cdr: ChangeDetectorRef,
  private prestamosService: PrestamosService,
  private juegoAccionesService: GameService,
  private clasificacionesService: CategoriasClasificacionesService
) {}

  verificarPrestamos() {
    const usuario = this.loginService.user();
    if (!usuario?.mail) return;

    this.prestamosService.tienePrestamosActivos(usuario.mail)
      .subscribe(resp => {
        this.tienePrestamoActivo = resp.tienePrestamosActivos;
      });
  }

  confirmarPrestamo(mailDueno: string) {
    const usuario = this.loginService.user();
    if (!usuario || !usuario.mail || !this.juego) return;

    const fecha = new Date().toISOString().split('T')[0];

    this.prestamosService.registrarPrestamo(
      mailDueno,
      usuario.mail,
      this.juego.id,
      fecha
    ).subscribe(() => {
      this.verificarPrestamos();
    });
  }
  devolverJuego() {
    const usuario = this.loginService.user();
    if (!usuario?.mail) return;

    this.prestamosService.devolverPrestamo(usuario.mail)
      .subscribe(() => {
        this.verificarPrestamos();
      });
  }
  comprarJuego(): void {
    const usuario = this.loginService.user();
    if (!usuario?.mail || !this.juego) return;

    this.juegoAccionesService.comprarJuego(
      this.juego.nombreJuego,
      usuario.mail
    ).subscribe({
      next: () => {
        this.comprado = true; 
        alert('Compra realizada con éxito');
      },
      error: (err) => {
        alert(err.error || 'Error al comprar el juego');
      }
    });
  }
  instalarJuego(): void {
    const usuario = this.loginService.user();
    if (!usuario?.mail || !this.juego) return;

    this.juegoAccionesService.instalarJuego(
      this.juego.nombreJuego,
      usuario.mail
    ).subscribe({
      next: (resp) => {
        if (resp.instalado) {
          this.instalado = true;
          alert('Juego instalado correctamente');
        } else {
          alert(resp.mensaje || 'No se pudo instalar');
        }
        this.cdr.detectChanges();
      },
      error: () => {
        alert('Error al intentar instalar');
      }
    });
    this.cdr.detectChanges();
  }
  desinstalarJuego(): void {
    const usuario = this.loginService.user();
    if (!usuario?.mail || !this.juego) return;

    this.juegoAccionesService
      .desinstalarJuego(this.juego.id, usuario.mail)
      .subscribe({
        next: (resp) => {
          if (resp.desinstalado) {
            this.instalado = false;
            alert('Juego desinstalado correctamente');
          } else {
            alert(resp.mensaje || 'No se pudo desinstalar');
          }
          this.cdr.detectChanges();
        },
        error: () => {
          alert('Error al intentar desinstalar');
        }
      });
      this.cdr.detectChanges();
  }

prestarJuego(): void {
  const usuario = this.loginService.user();
  if (!usuario?.mail || !this.juego) return;

  this.juegoAccionesService.instalarJuego(
    this.juego.nombreJuego,
    usuario.mail
  ).subscribe({
    next: (resp) => {

      // prestado
      if (resp.instalado) {
        this.instalado = true;
        alert('Juego instalado desde grupo familiar');
        return;
      }

      //No comprado
      if (resp.encontrado) {
        this.gruposDisponibles = resp.grupos ?? [];
        this.mensajePrestamo = resp.detalle ?? 'Disponible en grupos familiares';
      } else {
        alert(resp.detalle || 'No hay grupos familiares con el juego');
      }
    },
    error: () => {
      alert('Error al buscar préstamo');
    }
  });
}

  ngOnInit(): void {
      this.verificarPrestamos();
      const id = Number(this.route.snapshot.paramMap.get('id'));

      if (!id) {
        this.error = 'ID inválido';
        this.cargando = false;
        return;
      }
      if (this.loginService.isAdmin()) {
      window.location.href = `/app/admin/juego/${id}`;
      return;
    }

    if (this.loginService.isDesarrollador()) {
      window.location.href = `/app/home-dev/juego/${id}`;
      return;
    }

      this.juegoService.obtenerJuego(id).subscribe({
    next: juego => {
      this.juego = juego;
      this.cargando = false;

      this.cargarClasificaciones();
      this.cargarCategorias();

      this.cargarComentarios(id);
      this.verificarCompra(id);

      this.cdr.detectChanges();
    },
    error: () => {
      this.error = 'No se pudo cargar el juego';
      this.cargando = false;
    }
  });
  }
  cargarComentarios(idJuego: number): void {
  this.comentariosService.obtenerComentarios(idJuego).subscribe({
    next: (lista) => {
      this.comentarios = lista;
      this.comentariosRaiz = this.construirArbolComentarios(lista);
      this.cdr.detectChanges();
    },
    error: () => {
      console.error('Error al cargar comentarios');
    }
  });
}
cargarClasificaciones(): void {
  this.clasificacionesService.obtenerClasificaciones()
    .subscribe(lista => {
      this.clasificaciones = lista;

      const c = lista.find(
      x => x.idClasificacion === this.juego?.clasificacion
    );

    this.nombreClasificacion =
    c?.nombreCalsificacion ?? 'Sin clasificación';

      this.cdr.detectChanges();
    });
}
cargarCategorias(): void {
  this.clasificacionesService.obtenerCategorias()
    .subscribe((lista: Categoria[]) => {
      this.categorias = lista;

      const c = lista.find(
        x => x.idCategoria === this.juego?.categoria
      );

      this.nombreCategoria =
        c?.nombreCategoria ?? 'Sin categoría';

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
verificarCompra(idJuego: number): void {
  const usuario = this.loginService.user();

  if (!usuario?.mail) {
    this.comprado = false;
    this.instalado = false;
    return;
  }

  this.bibliotecaService.obtenerBiblioteca(usuario.mail).subscribe({
  next: (resp) => {
    const juegoEnBiblioteca = resp.biblioteca.find(
      (j: JuegoBiblioteca) => j.id === idJuego
    );

    if (juegoEnBiblioteca) {
      this.comprado = true;
      this.instalado = juegoEnBiblioteca.instalado;
    } else {
      this.comprado = false;
      this.instalado = false;
    }

    this.cdr.detectChanges();
  },
  error: () => {
    this.comprado = false;
    this.instalado = false;
  }
});

}

nuevoComentario = '';
calificacionJuego = 5;

publicarComentario(): void {
  if (!this.nuevoComentario.trim() || !this.juego) return;

  const usuario = this.loginService.user();
  if (!usuario?.mail) return;

  this.comentariosJuegosService.comentarJuego(
    this.nuevoComentario,
    this.juego.nombreJuego,
    usuario.mail,
    this.calificacionJuego
  ).subscribe({
    next: () => {
      this.nuevoComentario = '';
      this.calificacionJuego = 5;
      this.cargarComentarios(this.juego!.id);
    },
    error: () => {
      alert('Error al publicar comentario');
    }
  });
}
 onImgError(event: any) {
  const placeholder = 'assets/no-image.png'; 
    if (event.target.src !== placeholder) {
    event.target.src = placeholder;
  }
}
}
