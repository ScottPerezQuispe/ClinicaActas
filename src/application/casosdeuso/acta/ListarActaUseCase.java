package application.casosdeuso.acta;

import domain.entidades.Acta;
import domain.repositorio.IActaRepository;
import java.util.List;

public class ListarActaUseCase {
    private final IActaRepository repository;

    // Constructor que recibe la implementación del repositorio
    public ListarActaUseCase(IActaRepository repository) {
        this.repository = repository;
    }

    // Método para listar todas las áreas activas o todas según la implementación
    public List<Acta> Listar() {
        return repository.Listar();
    }
}
