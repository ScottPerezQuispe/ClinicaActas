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
public class HistorialMovimientos {
    private int idMovimiento;
    private int idEquipo; // Foreign Key to Equipo
    private int idUsuario; // Foreign Key to Usuario (Person responsible for the movement)
    private String tipoMovimiento;
    private Date fechaMovimiento;
    private String detalle;

    // Constructors
    public HistorialMovimientos() {}

    public HistorialMovimientos(int idMovimiento, int idEquipo, int idUsuario, String tipoMovimiento, Date fechaMovimiento, String detalle) {
        this.idMovimiento = idMovimiento;
        this.idEquipo = idEquipo;
        this.idUsuario = idUsuario;
        this.tipoMovimiento = tipoMovimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.detalle = detalle;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    
    
}
