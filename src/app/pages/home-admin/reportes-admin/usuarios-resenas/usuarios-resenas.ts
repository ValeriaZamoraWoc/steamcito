import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuarios-resenas.html',
  styleUrl: '../reportes-admin.css' 
})
export class UsuariosResenasComponent implements OnInit {

  usuarios: any[] = [];
  cargando = true;

  ngOnInit(): void {
    this.cargando = false;
  }
}
