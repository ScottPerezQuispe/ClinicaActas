/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

/**
 *
 * @author Scott.perez
 */
public class DetalleActa {
    private int IdDetalleActa;
    private int IdActa;

    public int getIdActa() {
        return IdActa;
    }

    public void setIdActa(int IdActa) {
        this.IdActa = IdActa;
    }
    // Mapeo al objeto Equipo completo (aunque en la DB solo se guarde IdEquipo)
    private int IdEquipo;

    // Constructors
    public DetalleActa() {}

    public int getIdDetalleActa() {
        return IdDetalleActa;
    }

    public void setIdDetalleActa(int IdDetalleActa) {
        this.IdDetalleActa = IdDetalleActa;
    }

    public int getIdEquipo() {
        return IdEquipo;
    }

    public void setIdEquipo(int IdEquipo) {
        this.IdEquipo = IdEquipo;
    }

  
   
    
}
