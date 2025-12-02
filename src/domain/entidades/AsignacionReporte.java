/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

/**
 *
 * @author Scott.perez
 */
public class AsignacionReporte {
    private int idAsignacion;
    private String fechaEntrega;
    private String nombreEquipo;
    private String modelo;
    private String empleado;
    private String area;

    public AsignacionReporte(int idAsignacion, String fechaEntrega,
                             String nombreEquipo, String modelo,
                             String empleado, String area) {
        this.idAsignacion = idAsignacion;
        this.fechaEntrega = fechaEntrega;
        this.nombreEquipo = nombreEquipo;
        this.modelo = modelo;
        this.empleado = empleado;
        this.area = area;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    
}
