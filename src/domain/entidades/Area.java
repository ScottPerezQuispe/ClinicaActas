package domain.entidades;

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
    
    @Override
    public String toString() {
        return this.Nombre; 
    }

  
    
}
