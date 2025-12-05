/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.repositorio;

import domain.entidades.Acta;
import domain.entidades.ReporteTrazabilidad;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public interface IActaRepository {
     boolean Registrar(Acta acta);
     Acta buscarPorId(int idActa);
     boolean aprobar(int idActa, int idCoordinador);
     boolean rechazar(int idActa, int idCoordinador);
     List<Acta> Listar(String Nombres);
     List<ReporteTrazabilidad> listarTrazabilidad(int idEquipo);
     
}
