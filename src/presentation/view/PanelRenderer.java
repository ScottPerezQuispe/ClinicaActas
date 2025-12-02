/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Scott.perez
 */
public class PanelRenderer extends JPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // La clave es devolver el componente que se va a pintar.
        if (value instanceof Component) {
             return (Component) value; 
        }
        // Si no es un componente (lo que causa el error de texto), devolver un panel vacío.
        return new JPanel(); 
    }
}
