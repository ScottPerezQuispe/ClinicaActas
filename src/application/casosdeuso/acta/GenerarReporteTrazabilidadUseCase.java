/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.entidades.ReporteTrazabilidad;
import domain.repositorio.IActaRepository;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public class GenerarReporteTrazabilidadUseCase {
    private IActaRepository repo;

    public GenerarReporteTrazabilidadUseCase(IActaRepository repo) {
        this.repo = repo;
    }

    public List<ReporteTrazabilidad> ejecutar(int idEquipo) {
        return repo.listarTrazabilidad(idEquipo);
    }
}
