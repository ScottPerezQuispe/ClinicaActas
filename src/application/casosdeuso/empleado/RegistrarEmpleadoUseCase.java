package application.casosdeuso.empleado;

import domain.entidades.Empleado;
import domain.repositorio.IEmpleadoRepository;

public class RegistrarEmpleadoUseCase {
    private final IEmpleadoRepository repository;

    public RegistrarEmpleadoUseCase(IEmpleadoRepository repository) {
        this.repository = repository;
    }

    public void Registrar(Empleado empleado) {
        repository.Insertar(empleado);
    }
}
