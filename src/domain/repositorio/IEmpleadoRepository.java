package domain.repositorio;

import domain.entidades.Empleado;
import java.util.List;

public interface IEmpleadoRepository {
    boolean Insertar(Empleado empleado);
    boolean Actualizar(Empleado empleado);
    boolean Eliminar(int idEmpleado);
    List<Empleado> Listar(String Nombres);
    List<Empleado> ListarCombo();
}
