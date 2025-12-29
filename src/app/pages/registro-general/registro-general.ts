import { Component,ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RegistroService } from '../../services/registro-service';
import { Router } from '@angular/router';;

@Component({
  selector: 'app-registrar-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro-general.html',
  styleUrl: './registro-general.css'
})
export class RegistrarUsuarioComponent {

  tipoUsuario: 'Comun' | 'Desarrollador' | 'Admin' = 'Comun';
  mail = '';
  nickname = '';
  contrasena = '';
  fechaNacimiento = '';

  // solo para usuario comun
  pais = '';
  telefono: number | null = null;

  // solo para desarrollador
  nombreEmpresa = '';
  descripcionEmpresa = '';
  empresaIdCreada: number | null = null;

  mensaje = '';
  error = '';

  constructor(private registroService: RegistroService,
        private cdr: ChangeDetectorRef,
        private router: Router
  ) {}

  registrar() {
    this.error = '';
    this.mensaje = '';

    if (this.tipoUsuario === 'Desarrollador' && !this.empresaIdCreada) {
      this.error = 'Debes registrar primero la empresa';
      return;
    }

    this.registroService.registrarUsuario({
      tipoUsuario: this.tipoUsuario,
      mail: this.mail,
      nickname: this.nickname,
      contrasena: this.contrasena,
      fechaNacimiento: this.fechaNacimiento,
      pais: this.pais,
      telefono: this.telefono!,
      idEmpresa: this.empresaIdCreada!
    }).subscribe({
    next: resp => {
      this.mensaje = 'Usuario registrado correctamente';
      this.cdr.detectChanges();

      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 1000);
    },
    error: err => {
      this.error = err.error || 'Error al registrar usuario';
      this.cdr.detectChanges();
    }
  });
}
registrarEmpresa() {
  if (!this.nombreEmpresa || !this.descripcionEmpresa) {
    this.error = 'Nombre y descripción de la empresa son obligatorios';
    return;
  }

  this.registroService.registrarEmpresa({
    nombre: this.nombreEmpresa,
    descripcion: this.descripcionEmpresa
  }).subscribe({
    next: (resp: string) => {
      this.mensaje = 'Empresa registrada. Ahora puedes crear el desarrollador.';
      this.empresaIdCreada = Number(resp); // convertimos el string devuelto a número
      this.cdr.detectChanges();
    },
    error: err => {
      this.error = err.error || 'Error al registrar empresa';
      this.cdr.detectChanges();
    }
  });
}
}
