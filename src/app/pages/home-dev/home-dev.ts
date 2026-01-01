import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user-service';
import { CompanyService, JuegoEmpresa } from '../../services/company-service';
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-home-dev',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './home-dev.html'
})
export class HomeDevComponent implements OnInit {
  ngOnInit(): void {
  }

}
