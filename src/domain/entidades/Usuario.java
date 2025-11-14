/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

import java.util.Date;

/**
 *
 * @author Scott.perez
 */
public class Usuario {
    private int IdUsuario;
    private int IdRol;
    private String NombreRol;
    private String NombreCompleto;
    private String Usuario;
    private String Clave;
    private int IdEstado;
    
   
    // 🔹 Constructores
    public Usuario() {
    }

    public Usuario(int IdUsuario, int IdRol, String NombreRol, String NombreCompleto, String Usuario, String Clave, int IdEstado) {
        this.IdUsuario = IdUsuario;
        this.IdRol = IdRol;
        this.NombreRol = NombreRol;
        this.NombreCompleto = NombreCompleto;
        this.Usuario = Usuario;
        this.Clave = Clave;
        this.IdEstado = IdEstado;
    }

    
    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String NombreRol) {
        this.NombreRol = NombreRol;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    
}
