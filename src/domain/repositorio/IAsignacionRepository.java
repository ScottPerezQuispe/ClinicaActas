/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.repositorio;

import domain.entidades.AsignacionReporte;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public interface IAsignacionRepository {
    List<AsignacionReporte> listarAsignacionesActivas(int IdEmpleado);
}
