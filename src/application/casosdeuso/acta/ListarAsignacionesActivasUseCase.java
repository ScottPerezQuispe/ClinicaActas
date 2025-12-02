/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.entidades.AsignacionReporte;
import domain.repositorio.IAsignacionRepository;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public class ListarAsignacionesActivasUseCase {
    private IAsignacionRepository repository;

    public ListarAsignacionesActivasUseCase(IAsignacionRepository repository) {
        this.repository = repository;
    }

    public List<AsignacionReporte> listarAsignacionesActivas(int IdEmpleado) {
        return repository.listarAsignacionesActivas(IdEmpleado);
    }
}
