import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-reportes-admin',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './reportes-admin.html',
  styleUrl: './reportes-admin.css' 
})
export class ReportesAdminComponent {

  constructor(private router: Router) {}

  irA(tipo: string): void {
    this.router.navigate([`/app/admin/reportes/${tipo}`]);
  }
}
