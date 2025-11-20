package application.casosdeuso.area;

import domain.entidades.Area;
import domain.repositorio.IAreaRepository;
import java.util.List;

public class ListarAreaUseCase {
    private final IAreaRepository repository;

    // Constructor que recibe la implementación del repositorio
    public ListarAreaUseCase(IAreaRepository repository) {
        this.repository = repository;
    }

    // Método para listar todas las áreas activas o todas según la implementación
    public List<Area> listar() {
        return repository.Listar();
    }
}
