/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.modelo;

/**
 *
 * @author Scott.perez
 */
public class DetalleActa {
     private int idDetalle;
    private int idActa; // Foreign Key to Acta
    private int idEquipo; // Foreign Key to Equipo
    private String estadoEntrega;
    private String observaciones;

    // Constructors
    public DetalleActa() {}

    public DetalleActa(int idDetalle, int idActa, int idEquipo, String estadoEntrega, String observaciones) {
        this.idDetalle = idDetalle;
        this.idActa = idActa;
        this.idEquipo = idEquipo;
        this.estadoEntrega = estadoEntrega;
        this.observaciones = observaciones;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
    
}
