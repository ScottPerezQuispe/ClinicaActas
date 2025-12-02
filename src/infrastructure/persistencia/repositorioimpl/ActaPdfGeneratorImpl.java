/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import com.itextpdf.text.BaseColor;
import domain.entidades.Acta;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import domain.entidades.Acta;
import domain.entidades.DetalleActa;
import java.io.FileOutputStream;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.itextpdf.text.Phrase; // Útil para celdas de tabla
import com.itextpdf.text.pdf.draw.LineSeparator;
import domain.repositorio.IActaPdfGenerator;
/**
 *
 * @author Scott.perez
 */
public class ActaPdfGeneratorImpl implements IActaPdfGenerator {

   @Override
    public String generarPDF(Acta acta) throws Exception {

        String ruta = "acta_" + acta.getIdActa() + ".pdf";

        
       


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(ruta));

        document.open();

        // Encabezado
        Paragraph title = new Paragraph("ACTA DE ENTREGA");
        
   
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

 
 

       // ==== DATOS GENERALES (SIN TABLA) ====
// 3. Título de la sección
        Paragraph generalTitle = new Paragraph("DATOS GENERALES");
        generalTitle.setSpacingAfter(10f); // Espacio después del título
        document.add(generalTitle);

        // ========== TABLA DE DATOS GENERALES (SIN BORDES) ==========
        
        // Tabla de 2 columnas para el formato de etiquetas y valores
        PdfPTable dataTable = new PdfPTable(4); // Usamos 4 columnas (Etiqueta, Valor, Etiqueta, Valor)
        dataTable.setWidthPercentage(100);
        // Distribución de anchos: 15% (Etiqueta) - 35% (Valor) - 15% (Etiqueta) - 35% (Valor)
        dataTable.setWidths(new float[]{0.20f, 0.30f, 0.20f, 0.30f}); 

        // Fila 1: Tipo de Acta y Empleado
dataTable.addCell(createNoBorderCell("Tipo de Acta:"));
dataTable.addCell(createNoBorderCell(acta.getTipoActa()));
dataTable.addCell(createNoBorderCell("Empleado:"));
dataTable.addCell(createNoBorderCell(acta.getEmpleadoNombres()));

// Fila 2: Id Acta y Área
dataTable.addCell(createNoBorderCell("Fecha de entrega:"));
dataTable.addCell(createNoBorderCell(acta.getFechaRegistro()));
dataTable.addCell(createNoBorderCell("Área:"));
dataTable.addCell(createNoBorderCell(acta.getEmpleadoArea()));

// Fila 3: Registrado por y Fecha Registro
dataTable.addCell(createNoBorderCell("Registrado por:"));
dataTable.addCell(createNoBorderCell(acta.getRegistradorUsuario()));
dataTable.addCell(createNoBorderCell("Fecha Registro:"));
dataTable.addCell(createNoBorderCell(acta.getFechaSoporte()));


// Fila 4: Registrado por y Fecha Registro
dataTable.addCell(createNoBorderCell("Aprobado por:"));
dataTable.addCell(createNoBorderCell(acta.getAprobadorUsuario()));
dataTable.addCell(createNoBorderCell("Fecha Aprobado:"));
dataTable.addCell(createNoBorderCell(acta.getFechaCoordinador()));


        document.add(dataTable);
        
      // Crea un párrafo con una línea de separación
Paragraph separator = new Paragraph(" "); 
separator.setSpacingBefore(10f); // Espacio antes de la línea
separator.setSpacingAfter(10f);  // Espacio después de la línea

// Usar un separador de iText (Requiere importación: com.itextpdf.text.pdf.draw.LineSeparator)
// Asegúrate de importar la clase LineSeparator

LineSeparator line = new LineSeparator(1f, 100, BaseColor.GRAY, Element.ALIGN_CENTER, -1);
separator.add(line); 

document.add(separator);


document.add(new Paragraph("ENTREGA DE EQUIPO"));
        document.add(new Paragraph("\n"));
        
        
        // ========== TABLA DE EQUIPOS ==========
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{33, 33, 33});
        
        table.addCell("Equipo");
        table.addCell("Marca");
        table.addCell("Modelo");

        for (var det : acta.getDetalles()) {
            table.addCell(det.getEquipo().getNombreEquipo());
            table.addCell(det.getEquipo().getMarca());
            table.addCell(det.getEquipo().getModelo());
        }

        document.add(table);

        
        document.add(new Paragraph("\n"));
        
        // ========== COMENTARIO ==========
        Paragraph obsTitle = new Paragraph("Comentario:");
        document.add(obsTitle);

        Paragraph obsValue = new Paragraph(acta.getComentario() == null ? "" : acta.getComentario());
        document.add(obsValue);
        
        // Pie de firma
        document.add(new Paragraph("\n\n_____________________________"));
        document.add(new Paragraph("Firma del usuario"));

        document.close();

        return ruta;
    }

    

 // Dentro de la clase ActaPdfGeneratorImpl {...}
private PdfPCell createNoBorderCell(String content) {
    // Asegúrate de importar com.itextpdf.text.Phrase
  PdfPCell cell = new PdfPCell(new Phrase(content));
    cell.setBorder(PdfPCell.NO_BORDER);
    cell.setPadding(3f);
    return cell;
}
    
}
