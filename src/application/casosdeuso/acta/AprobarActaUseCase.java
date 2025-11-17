/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.repositorio.IActaRepository;

/**
 *
 * @author Scott.perez
 */
public class AprobarActaUseCase {
    private final IActaRepository actaRepository;

    public AprobarActaUseCase(IActaRepository actaRepository) {
        this.actaRepository = actaRepository;
    }

    public boolean ejecutar(int idActa, int idCoordinador) {
        return actaRepository.aprobar(idActa, idCoordinador);
    }
}
