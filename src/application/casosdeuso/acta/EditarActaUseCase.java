/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.entidades.Acta;
import domain.repositorio.IActaRepository;

/**
 *
 * @author Scott.perez
 */
public class EditarActaUseCase {
    private final IActaRepository repository;

    public EditarActaUseCase(IActaRepository repository) {
        this.repository = repository;
    }

    public Acta buscarPorId(int idActa) {
        return repository.buscarPorId(idActa);
    }
}
