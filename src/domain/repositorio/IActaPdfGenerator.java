/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.repositorio;

import domain.entidades.Acta;

/**
 *
 * @author Scott.perez
 */
public interface IActaPdfGenerator {
    String generarPDF(Acta acta)throws Exception;
}
