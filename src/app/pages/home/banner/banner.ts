import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { BannerService } from '../../../services/banner-service';
import { Juego } from '../../../services/game-service';

@Component({
  selector: 'app-banner-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './banner.html',
  styleUrl: './banner.css'
})
export class BannerHomeComponent implements OnInit {

  banner: Juego | null = null;

  constructor(
    private bannerService: BannerService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.bannerService.obtenerBanner().subscribe({
      next: (juego) => this.banner = juego,
      error: () => this.banner = null
    });
    this.cdr.detectChanges();
  }

  irAlJuego(): void {
    if (this.banner) {
      this.router.navigate(['/app/juego', this.banner.id]);
    }
    this.cdr.detectChanges();
  }
}
