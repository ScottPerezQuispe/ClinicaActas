/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.repositorio;

import domain.entidades.Equipo;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public interface IEquipoRepository {
      List<Equipo> Listar(String Nombres);
      boolean Insertar(Equipo equipo);
      boolean Actualizar(Equipo equipo);
      boolean Eliminar(int idEquipo);
      List<Equipo> ListarCombo(int IdEmpleado);
      List<Equipo> ListarEquiposPorEmpleado(int IdEmpleado);
      
}
