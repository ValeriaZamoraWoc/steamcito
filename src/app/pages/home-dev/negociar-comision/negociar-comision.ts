import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service';
import { PeticionComisionService } from '../../../services/peticion-comision-service';

@Component({
  selector: 'app-negociar-comision',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './negociar-comision.html',
  styleUrl: './negociar-comision.css'
})
export class NegociarComisionComponent {

  porcentaje: number | null = null;

  mensaje = '';
  error = '';

  constructor(
    private loginService: UserService,
    private peticionService: PeticionComisionService
  ) {}

  registrarPeticion() {
    this.mensaje = '';
    this.error = '';

    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      return;
    }

    if (this.porcentaje === null || this.porcentaje <= 0) {
      this.error = 'Ingrese un porcentaje válido';
      return;
    }

    this.peticionService.registrarPeticion({
      empresa: idEmpresa,
      porcentaje: this.porcentaje
    }).subscribe({
      next: msg => {
        this.mensaje = msg;
        this.porcentaje = null;
      },
      error: err => {
        this.error = err.error || 'Error al registrar la petición';
      }
    });
  }
}
