package application.casosdeuso.empleado;

import domain.entidades.Empleado;
import domain.repositorio.IEmpleadoRepository;

public class ActualizarEmpleadoUseCase {
    private final IEmpleadoRepository repository;

    public ActualizarEmpleadoUseCase(IEmpleadoRepository repository) {
        this.repository = repository;
    }

    public void Actualizar(Empleado empleado) {
        repository.Actualizar(empleado);
    }
}

