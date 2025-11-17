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
public class RechazarActaUseCase {
    private final IActaRepository actaRepository;

    public RechazarActaUseCase(IActaRepository actaRepository) {
        this.actaRepository = actaRepository;
    }

    // Recibe el ID del acta, el ID del coordinador, y el comentario actualizado
    public boolean ejecutar(int idActa, int idCoordinador) {
        
        return actaRepository.rechazar(idActa, idCoordinador);
    }
}
