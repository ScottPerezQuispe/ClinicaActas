package application.casosdeuso.estado;

import domain.entidades.Estado;
import domain.repositorio.IEstadoRepository;
import java.util.List;

public class ListarEstadoUseCase {
    private final IEstadoRepository repository;

    // Constructor que recibe la implementación del repositorio
    public ListarEstadoUseCase(IEstadoRepository repository) {
        this.repository = repository;
    }

    // Método para listar todas las áreas activas o todas según la implementación
    public List<Estado> listar() {
        return repository.Listar();
    }
}
