import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ObtenerEstadoWalletService, Wallet } from '../../../services/wallet-service';
import { LoginService } from '../../../services/login-service';
import { FormsModule } from '@angular/forms';
import { AgregarSaldoWalletService } from '../../../services/agregar-saldo.wallet-service';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './estado-wallet.html',
  styleUrl: './estado-wallet.css'
})
export class WalletComponent implements OnInit {

  wallet: Wallet | null = null;
  error: string | null = null;
  cargando = true;

  montoAgregar: number | null = null;
  mensajeExito: string | null = null;
  procesando = false;

  constructor(
    private walletService: ObtenerEstadoWalletService,
    private agregarSaldoService: AgregarSaldoWalletService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarWallet();
  }

  cargarWallet(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      this.cdr.detectChanges();
      return;
    }

    this.walletService.obtenerEstadoWallet(usuario.mail).subscribe({
      next: (resp) => {
        if (resp.exito && resp.wallet) {
          this.wallet = resp.wallet;
        } else {
          this.error = resp.mensaje ?? 'No se pudo obtener la wallet';
        }
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al conectar con el servidor';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  agregarSaldo(): void {
    if (!this.wallet || !this.montoAgregar || this.montoAgregar <= 0) {
      this.error = 'Ingresa un monto válido';
      return;
    }

    this.procesando = true;
    this.error = null;
    this.mensajeExito = null;

    this.agregarSaldoService
      .agregarSaldo(this.wallet.mail, this.montoAgregar)
      .subscribe({
        next: () => {
          this.mensajeExito = 'Saldo agregado correctamente';
          this.montoAgregar = null;

          this.cargarWallet();
          this.procesando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Error al agregar saldo';
          this.procesando = false;
        }
      });
  }
}
