/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public class Acta {
   private int IdActa;
    private Date Fecha; 
    private String TipoActa; // Referencia al tipo de acta (Entrega/Recojo)
    
    // Mapeo de FKs a objetos de Dominio para mejor manejo
    private int IdEmpleado; // Asumiendo que existe la clase Empleado
    private int IdUsuarioSoporte; // Asumiendo que existe la clase Usuario
    private int IdCoordinador; // Asumiendo que existe la clase Usuario
    
    private String FechaSoporte;
    private String FechaCoordinador;
    private String Comentario;
    private int IdEstado;
    // 👉 Aquí está la lista de detalles del acta
    private List<DetalleActa> Detalles;
    
    //Datos Adicionales
     private String EmpleadoNombres;

    private String EmpleadoArea;
    private String RegistradorUsuario;
    private String FechaRegistro;
     private String AprobadorUsuario;

  
     
    
    public Acta() {}

    public Acta(Date Fecha, String TipoActa, int IdEmpleado, int IdUsuarioSoporte, String Comentario, List<DetalleActa> Detalles) {
        this.Fecha = Fecha;
        this.TipoActa = TipoActa;
        this.IdEmpleado = IdEmpleado;
        this.IdUsuarioSoporte = IdUsuarioSoporte;
        this.Comentario = Comentario;
        this.Detalles = Detalles;
    }

    public Acta(String FechaRegistro, String TipoActa, String Comentario, String EmpleadoNombres, String EmpleadoArea,
            String RegistradorUsuario, String FechaSoporte, String AprobadorUsuario, String FechaCoordinador,List<DetalleActa> Detalles) {
        this.FechaRegistro = FechaRegistro;
        this.TipoActa = TipoActa;
        this.Comentario = Comentario;
        this.EmpleadoNombres = EmpleadoNombres;
        this.EmpleadoArea = EmpleadoArea;
        this.RegistradorUsuario = RegistradorUsuario;
        this.FechaSoporte = FechaSoporte;
        this.AprobadorUsuario = AprobadorUsuario;
        this.FechaCoordinador = FechaCoordinador;
        this.Detalles = Detalles;
    }
    
    

   

    public int getIdActa() {
        return IdActa;
    }

    public void setIdActa(int IdActa) {
        this.IdActa = IdActa;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTipoActa() {
        return TipoActa;
    }

    public void setTipoActa(String TipoActa) {
        this.TipoActa = TipoActa;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public int getIdUsuarioSoporte() {
        return IdUsuarioSoporte;
    }

    public void setIdUsuarioSoporte(int IdUsuarioSoporte) {
        this.IdUsuarioSoporte = IdUsuarioSoporte;
    }

    public int getIdCoordinador() {
        return IdCoordinador;
    }

    public void setIdCoordinador(int IdCoordinador) {
        this.IdCoordinador = IdCoordinador;
    }

    public String getFechaSoporte() {
        return FechaSoporte;
    }

    public void setFechaSoporte(String FechaSoporte) {
        this.FechaSoporte = FechaSoporte;
    }

    public String getFechaCoordinador() {
        return FechaCoordinador;
    }

    public void setFechaCoordinador(String FechaCoordinador) {
        this.FechaCoordinador = FechaCoordinador;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String Comentario) {
        this.Comentario = Comentario;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public List<DetalleActa> getDetalles() {
        return Detalles;
    }

    public void setDetalles(List<DetalleActa> Detalles) {
        this.Detalles = Detalles;
    }

     public String getEmpleadoNombres() {
        return EmpleadoNombres;
    }

    public void setEmpleadoNombres(String EmpleadoNombres) {
        this.EmpleadoNombres = EmpleadoNombres;
    }

    public String getEmpleadoArea() {
        return EmpleadoArea;
    }

    public void setEmpleadoArea(String EmpleadoArea) {
        this.EmpleadoArea = EmpleadoArea;
    }

    public String getRegistradorUsuario() {
        return RegistradorUsuario;
    }

    public void setRegistradorUsuario(String RegistradorUsuario) {
        this.RegistradorUsuario = RegistradorUsuario;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }
    
     public String getAprobadorUsuario() {
        return AprobadorUsuario;
    }

    public void setAprobadorUsuario(String AprobadorUsuario) {
        this.AprobadorUsuario = AprobadorUsuario;
    }

   
    
}
