/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.equipo;

import domain.entidades.Equipo;
import domain.repositorio.IEquipoRepository;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public class ListarEquipoUseCase {
    private final IEquipoRepository Repository;

    public ListarEquipoUseCase(IEquipoRepository Repository) {
        this.Repository = Repository;
    }

    public List<Equipo> Listar() {
        return Repository.Listar();
    }
}
