package domain.entidades;

public class Estado {
    private int IdEstado;
    private String Nombre;
    private String Descripcion;
    
    public Estado(){
    }
    public Estado(int IdEstado, String Nombre, String Descripcion) {
        this.IdEstado = IdEstado;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String isDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    @Override
    public String toString() {
        return this.Nombre; 
    }

}
