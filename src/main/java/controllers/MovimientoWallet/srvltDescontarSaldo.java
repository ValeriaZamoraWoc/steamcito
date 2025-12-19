/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.MovimientoWallet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.MovimientoWallet.MovimientoWallet;

/**
 *
 * @author cacerola
 */
@WebServlet("/descontarSaldo")
public class srvltDescontarSaldo extends HttpServlet{
    private MovimientoWallet servicio = new MovimientoWallet();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mail = request.getParameter("mail");
        String montoSTR = request.getParameter("monto");
        
        Integer monto = Integer.parseInt(montoSTR);
        
        servicio.descontarSaldo(mail, monto);
    }
}
