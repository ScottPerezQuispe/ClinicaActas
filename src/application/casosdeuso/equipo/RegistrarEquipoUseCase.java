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
public class RegistrarEquipoUseCase {
    private final IEquipoRepository Repository;

    public RegistrarEquipoUseCase(IEquipoRepository Repository) {
        this.Repository = Repository;
    }

    public boolean ejecutar(Equipo equipo) {
        return Repository.Insertar(equipo);
    }
}
