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
public class AsignacionEquipos {
     private int idAsignacion;
    private int idEquipo; // Foreign Key to Equipo
    private int idUsuario; // Foreign Key to Usuario
    private int idActa; // Foreign Key to Acta
    private Date fechaAsignacion;

    // Constructors
    public AsignacionEquipos() {}

    public AsignacionEquipos(int idAsignacion, int idEquipo, int idUsuario, int idActa, Date fechaAsignacion) {
        this.idAsignacion = idAsignacion;
        this.idEquipo = idEquipo;
        this.idUsuario = idUsuario;
        this.idActa = idActa;
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
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

    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
    
    
}
