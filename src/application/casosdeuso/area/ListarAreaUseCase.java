/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.area;

import domain.entidades.Area;
import domain.repositorio.IAreaRepository;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
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
