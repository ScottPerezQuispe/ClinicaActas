package application.casosdeuso.empleado;

import domain.repositorio.IEmpleadoRepository;

public class EliminarEmpleadoUseCase {
    private final IEmpleadoRepository repository;

    public EliminarEmpleadoUseCase(IEmpleadoRepository repository) {
        this.repository = repository;
    }

    public void Eliminar(int idEmpleado) {
        repository.Eliminar(idEmpleado);
    }
}
