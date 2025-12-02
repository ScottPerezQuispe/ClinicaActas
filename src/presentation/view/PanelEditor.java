/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Scott.perez
 */
public class PanelEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel;
    private JButton currentButton;
    private int currentIdActa;
    private FrmBandejaActa parentBandeja;

    public PanelEditor(FrmBandejaActa parentBandeja) {
        this.parentBandeja = parentBandeja;
    }
    
    // Método que se llama cuando se prepara la edición de la celda
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel = (JPanel) value;
        
        // 🔑 CLAVE: Obtener el ID del Acta de la fila
        currentIdActa = (int) table.getModel().getValueAt(row, 0); // Asume que la Columna 0 es el ID

        // 🔑 CLAVE: Reasignar el ActionListener al botón
        // (Esto es necesario porque el editor se reusa)
        
        // Asumiendo que el botón "A" (Ver/Aprobar) es el primer componente en el JPanel
        if (panel.getComponentCount() > 0 && panel.getComponent(0) instanceof JButton) {
            currentButton = (JButton) panel.getComponent(0);
            
            // ELIMINAMOS TODOS LOS LISTENERS PREVIOS
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
            
            // AÑADIMOS EL NUEVO LISTENER QUE USA EL ID DE LA FILA ACTUAL
            currentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cierra la edición de la tabla
                    fireEditingStopped(); 
                    
                    // Llama al método de apertura de FrmBandejaActa
                    parentBandeja.abrirVistaActa(currentIdActa); 
                }
            });
        }
        
        return panel;
    }

    // Método que devuelve el valor de la celda después de la edición
    @Override
    public Object getCellEditorValue() {
        return panel;
    }
}
