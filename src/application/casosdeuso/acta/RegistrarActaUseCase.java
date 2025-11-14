/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.entidades.Acta;
import domain.repositorio.IActaRepository;
import domain.repositorio.IEmpleadoRepository;

/**
 *
 * @author Scott.perez
 */
public class RegistrarActaUseCase {
    private final IActaRepository repository;

    public RegistrarActaUseCase(IActaRepository repository) {
        this.repository = repository;
    }

    public void Registrar(Acta acta) {
        repository.Registrar(acta);
    }
}
