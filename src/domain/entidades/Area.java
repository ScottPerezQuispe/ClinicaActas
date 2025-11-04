/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

/**
 *
 * @author Scott.perez
 */
public class Area {
    private int IdArea;
    private String Nombre;
    private boolean Activo;

    public Area(){
    }
    public Area(int IdArea, String Nombre, boolean Activo) {
        this.IdArea = IdArea;
        this.Nombre = Nombre;
        this.Activo = Activo;
    }

    public int getIdArea() {
        return IdArea;
    }

    public void setIdArea(int IdArea) {
        this.IdArea = IdArea;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

  
    
    
}
