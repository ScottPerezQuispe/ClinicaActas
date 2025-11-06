/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.entidades;

import java.sql.Date;

/**
 *
 * @author Scott.perez
 */
public class Equipo {
    private int IdEquipo;
    private String Nombre;
    private String Marca; 
    private String Modelo;
    private int Cantidad;
 
   
    
    // Constructors
    public Equipo() {}


    public Equipo(int IdEquipo, String Nombre, String Marca, String Modelo, int Cantidad) {
        this.IdEquipo = IdEquipo;
        this.Nombre = Nombre;
        this.Marca = Marca;
        this.Modelo = Modelo;
        this.Cantidad = Cantidad;
     
    }

    
    
    public int getIdEquipo() {
        return IdEquipo;
    }

    public void setIdEquipo(int IdEquipo) {
        this.IdEquipo = IdEquipo;
    }

    public String getNombreEquipo() {
        return Nombre;
    }

    public void setNombreEquipo(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

  @Override
    public String toString() {
        // Devuelve el nombre del equipo, que es lo que el usuario debe ver.
        return this.Nombre; 
    }

  
    
}
