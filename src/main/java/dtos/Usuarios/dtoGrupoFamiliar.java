/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Usuarios;

import java.util.List;


/**
 *
 * @author cacerola
 */
public class dtoGrupoFamiliar {

    private String nombreGrupo;
    private int idGrupoFamiliar;
    private List<String> mailsIntegrantes;

    public void setIdGrupoFamiliar(int idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    public void setMailsIntegrantes(List<String> mailsIntegrantes) {
        this.mailsIntegrantes = mailsIntegrantes;
    }

    public int getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    public List<String> getMailsIntegrantes() {
        return mailsIntegrantes;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    
    
}
