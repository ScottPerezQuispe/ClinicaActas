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
    private int idUsuario;
    private int idPersonal;
    private int idRol;
    private String usuario;
    private String clave;
    private int idEstado;

    // 🔹 Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, int idPersonal, int idRol, String usuario, String clave, int idEstado) {
        this.idUsuario = idUsuario;
        this.idPersonal = idPersonal;
        this.idRol = idRol;
        this.usuario = usuario;
        this.clave = clave;
        this.idEstado = idEstado;
    }

    // 🔹 Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    // 🔹 Métodos adicionales (opcional)
    public boolean estaActivo() {
        return idEstado == 1;
    }
}
