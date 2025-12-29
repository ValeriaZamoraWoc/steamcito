import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home-admin',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './home-admin.html',
  styleUrl: './home-admin.css',
})
export class HomeAdmin {}
