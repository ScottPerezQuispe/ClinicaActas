/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.equipo;

import domain.entidades.Equipo;
import domain.repositorio.IEquipoRepository;

/**
 *
 * @author Scott.perez
 */
public class EliminarEquipoUseCase {
    private final IEquipoRepository Repository;

    public EliminarEquipoUseCase(IEquipoRepository Repository) {
        this.Repository = Repository;
    }

    public boolean Eliminar(int idEquipo) {
        return Repository.Eliminar(idEquipo);
    }
}
