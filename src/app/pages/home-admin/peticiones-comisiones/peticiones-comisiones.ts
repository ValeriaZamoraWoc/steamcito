import { Component, OnInit ,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeticionComision, PeticionComisionService } from '../../../services/peticion-comision-service';


@Component({
  selector: 'app-peticiones-comisiones',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './peticiones-comisiones.html',
  styleUrl: './peticiones-comisiones.css'
})
export class PeticionesComisionesComponent implements OnInit {

  peticiones: PeticionComision[] = [];
  cargando = true;
  error: string | null = null;

  constructor(private peticionesService: PeticionComisionService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarPeticiones();
  }

  cargarPeticiones(): void {
    this.cargando = true;
    this.peticionesService.obtenerPeticiones().subscribe({
      next: (data) => {
        this.peticiones = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar peticiones';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  aceptar(idEmpresa: number): void {
    this.peticionesService.aceptarPeticion(idEmpresa).subscribe(() => {
      this.peticiones = this.peticiones.filter(
        p => p.idEmpresa !== idEmpresa
      );
    });
  }

  rechazar(idEmpresa: number): void {
    this.peticionesService.rechazarPeticion(idEmpresa).subscribe(() => {
      this.peticiones = this.peticiones.filter(
        p => p.idEmpresa !== idEmpresa
      );
    });
  }
}
