import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './feedback.html'
})
export class ReporteFeedbackDevComponent {}
