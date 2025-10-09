/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.modelo;

import java.util.Date;

/**
 *
 * @author Scott.perez
 */
public class Equipo {
     private int idEquipo;
    private String codigoInterno;
    private String descripcionEq; // Assuming Eq means Equipo (Equipment)
    private String categoria;
    private String estado;
    private String observaciones;
    private Date fechaRegistro;
    private int disponibilidad; // Assuming this is an int flag (e.g., 0=No, 1=Yes)

    
    // Constructors
    public Equipo() {}

    public Equipo(int idEquipo, String codigoInterno, String descripcionEq, String categoria, String estado, String observaciones, Date fechaRegistro, int disponibilidad) {
        this.idEquipo = idEquipo;
        this.codigoInterno = codigoInterno;
        this.descripcionEq = descripcionEq;
        this.categoria = categoria;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
        this.disponibilidad = disponibilidad;
    }
    
    
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getDescripcionEq() {
        return descripcionEq;
    }

    public void setDescripcionEq(String descripcionEq) {
        this.descripcionEq = descripcionEq;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    
}
