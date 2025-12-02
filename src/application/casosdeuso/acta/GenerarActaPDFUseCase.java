/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.casosdeuso.acta;

import domain.entidades.Acta;
import domain.repositorio.IActaPdfGenerator;

/**
 *
 * @author Scott.perez
 */
public class GenerarActaPDFUseCase {
     private final IActaPdfGenerator pdfGenerator;

    public GenerarActaPDFUseCase(IActaPdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    public String ejecutar(Acta acta) throws Exception {
        return pdfGenerator.generarPDF(acta);
    }
}

