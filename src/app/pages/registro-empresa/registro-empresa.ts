import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RegistroEmpresaService } from '../../services/registro-empresa.service';

@Component({
  selector: 'app-registro-empresa',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registro-empresa.html',
  styleUrl: './registro-empresa.css',
})
export class RegistroEmpresa {

  nombre = '';
  descripcion = '';

  constructor(private empresaService: RegistroEmpresaService) {}

  async registrar() {
    console.log('CLICK EN REGISTRAR');
    console.log(this.nombre, this.descripcion);

    await this.empresaService.registrarEmpresa(
      this.nombre,
      this.descripcion
    );

    alert('Empresa registrada correctamente');
  }
}
