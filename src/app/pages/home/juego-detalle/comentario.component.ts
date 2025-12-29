import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Comentario } from '../../../services/obtener-comentarios-por-juego-service';
import { FormsModule } from '@angular/forms';
import { ComentariosJuegoService } from '../../../services/comentar-juego-service';
import { LoginService } from '../../../services/login-service';


@Component({
  selector: 'app-comentario',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './comentario.component.html',
  styleUrl: './juego-detalle.css' 
})
export class ComentarioComponent {

  constructor(
    private comentariosService: ComentariosJuegoService,
    private loginService: LoginService
  ) {}

  
  @Input() comentario!: Comentario;
  @Input() puedeResponder = false;
  @Input() nombreJuego!: string;
  @Input() puedeOcultar = false;
  @Output() comentarioOcultado = new EventEmitter<void>();
  
  mostrarRespuesta = false;
  respuesta = '';
  calificacion = 5;

  responder(): void {
    if (!this.respuesta.trim()) return;

    const usuario = this.loginService.user();
    if (!usuario?.mail) return;

    this.comentariosService.comentarComentario(
      this.respuesta,
      this.nombreJuego,   
      usuario.mail,
      this.comentario.idComentario,
      this.calificacion
    ).subscribe({
      next: () => {
        this.respuesta = '';
        this.mostrarRespuesta = false;
      },
      error: () => {
        alert('Error al responder comentario');
      }
    });
  }
  ocultarComentario(): void {
    if (!confirm('¿Ocultar este comentario?')) return;

    this.comentariosService
      .deshabilitarComentario(this.comentario.idComentario)
      .subscribe({
        next: () => {
          this.comentarioOcultado.emit();
        },
        error: () => {
          alert('Error al ocultar comentario');
        }
      });
  }

}
