package models.ObjetosUsuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Wallet {

    private int id;
    private int saldo;

    public Wallet(){
        this.saldo = 0;
    }

    public Wallet(int saldoInicial){
        if(saldoInicial < 0){
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    public int getId(){
        return id;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public int getSaldo(){
        return saldo;
    }

    public boolean agregarSaldo(int monto){
        if(monto <= 0){
            return false;
        }
        this.saldo += monto;
        return true;
    }

    public boolean gastarSaldo(int monto){
        if(monto <= 0){
            return false;
        }
        if(monto > saldo){
            return false;
        }
        this.saldo -= monto;
        return true;
    }
}
