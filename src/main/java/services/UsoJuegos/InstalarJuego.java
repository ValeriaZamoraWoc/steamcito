/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDGrupoFamiliar;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoBiblioteca;
import dtos.Usuarios.dtoUsuarioComun;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cacerola
 */
public class InstalarJuego {
    private CRUDJuego cJ = new CRUDJuego();
    private CRUDBiblioteca cB = new CRUDBiblioteca();
    private CRUDUsuario cU = new CRUDUsuario();
    private CRUDGrupoFamiliar cG = new CRUDGrupoFamiliar();
    
    public Map<String, Object> intentarInstalarJuego(String nombreJuego, String mail) {

        Map<String, Object> respuesta = new HashMap<>();

        dtoJuego juego = cJ.buscarJuegoPorTitulo(nombreJuego);
        dtoBiblioteca biblio = cB.buscarBibliotecaPorMail(mail);

        // Si lo tiene comprado → instalar
        if (cB.buscarJuegoEnBiblioteca(biblio, juego)) {
            dtoUsuarioComun usuario = cU.buscarUsuarioComunPorMail(mail);
            cB.instalarJuego(usuario, juego);

            respuesta.put("instalado", true);
            return respuesta;
        }

        // ❌ No lo tiene comprado
        respuesta.put("instalado", false);
        respuesta.put("mensaje", "No tienes el juego comprado");

        List<Integer> grupos = cG.gruposQueTienenJuego(mail, juego.getId());

        if (grupos.isEmpty()) {
            respuesta.put("encontrado", false);
            respuesta.put("detalle", "No se encontró en ningún grupo familiar");
        } else {
            respuesta.put("encontrado", true);
            respuesta.put("detalle", "Se encontró en los siguientes grupos");
            respuesta.put("grupos", grupos);
        }

        return respuesta;
    }

}
