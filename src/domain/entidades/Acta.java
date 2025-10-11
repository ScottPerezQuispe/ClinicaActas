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
public class Acta {
    private int idActa;
    private String numActa;
    private Date fechaActa;
    private int idUsuario; // Foreign Key to Usuario (User who created/authorized)
    private String solicitante;
    private String observaciones;
    private String archivoPDF;
    
     public Acta() {}

    public Acta(int idActa, String numActa, Date fechaActa, int idUsuario, String solicitante, String observaciones, String archivoPDF) {
        this.idActa = idActa;
        this.numActa = numActa;
        this.fechaActa = fechaActa;
        this.idUsuario = idUsuario;
        this.solicitante = solicitante;
        this.observaciones = observaciones;
        this.archivoPDF = archivoPDF;
    }

    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
    }

    public String getNumActa() {
        return numActa;
    }

    public void setNumActa(String numActa) {
        this.numActa = numActa;
    }

    public Date getFechaActa() {
        return fechaActa;
    }

    public void setFechaActa(Date fechaActa) {
        this.fechaActa = fechaActa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getArchivoPDF() {
        return archivoPDF;
    }

    public void setArchivoPDF(String archivoPDF) {
        this.archivoPDF = archivoPDF;
    }
    
    
    
}
