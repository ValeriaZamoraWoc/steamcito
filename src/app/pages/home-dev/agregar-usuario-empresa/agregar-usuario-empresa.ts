import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-agregar-usuario-empresa',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agregar-usuario-empresa.html',
  styleUrl: './agregar-usuario-empresa.css'
})
export class AgregarUsuarioEmpresaComponent {

  mail = '';
  nickname = '';
  contrasena = '';
  fechaNacimiento = '';

  mensaje = '';
  error = '';

  constructor(
    private loginService: UserService,
    private registroUsuariosService: UserService
  ) {}

  registrar() {
    this.mensaje = '';
    this.error = '';

    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa del usuario actual';
      return;
    }

    if (!this.mail || !this.nickname || !this.contrasena || !this.fechaNacimiento) {
      this.error = 'Todos los campos son obligatorios';
      return;
    }

    this.registroUsuariosService.registrarDesarrolladorEmpresa({
      mail: this.mail,
      nickname: this.nickname,
      contrasena: this.contrasena,
      fechaNacimiento: this.fechaNacimiento,
      empresa: idEmpresa
    }).subscribe({
      next: msg => {
        this.mensaje = msg;
        this.limpiarFormulario();
      },
      error: err => {
        this.error = err.error || 'Error al registrar usuario';
      }
    });
  }

  limpiarFormulario() {
    this.mail = '';
    this.nickname = '';
    this.contrasena = '';
    this.fechaNacimiento = '';
  }
}
