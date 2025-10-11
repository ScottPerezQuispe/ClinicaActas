/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.usuario;

import domain.entidades.Usuario;
import domain.repositorio.IUsuarioRepository;

/**
 *
 * @author Scott.perez
 */
public class ValidarLoginUseCase {
    private final IUsuarioRepository repository;

    public ValidarLoginUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario ejecutar(String usuario, String clave) {
        return repository.validarLogin(usuario, clave);
    }
}
