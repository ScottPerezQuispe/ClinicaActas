package application.casosdeuso.empleado;

import domain.entidades.Empleado;
import domain.repositorio.IEmpleadoRepository;
import java.util.List;

public class ListarEmpleadoUseCase {
    private final IEmpleadoRepository Repository;

    public ListarEmpleadoUseCase(IEmpleadoRepository repository) {
        this.Repository = repository;
    }

    public List<Empleado> Listar() {
        return Repository.Listar();
    }
}
