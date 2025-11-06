package domain.entidades;

public class Empleado {
    private int idEmpleado;
    private String dni;
    private String nombres;
    private String apellidos;
   private Area area; // Relación con la entidad Area

    // Constructor vacío
    public Empleado() {}

    // Constructor con parámetros
    public Empleado(int idEmpleado, String dni, String nombres, String apellidos, Area area) {
        this.idEmpleado = idEmpleado;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.area = area;
    }

    // Getters y Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    // Método auxiliar para mostrar nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    // 🎯 Agrega o Asegúrate de que este método exista:
    @Override
    public String toString() {
        // Esto es lo que el JComboBox usará para la visualización.
        return this.getNombreCompleto(); 
        // Alternativamente, puedes usar: return this.nombres + " " + this.apellidos;
    }
}