/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view;
import domain.entidades.Equipo;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultCellEditor; // <--- DEBE ESTAR IMPORTADO
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author Scott.perez
 */
public class EquipoCellEditor extends DefaultCellEditor {
    
    // El JComboBox real que se mostrará
    private final JComboBox<Equipo> equipoComboBox;
    
    // Referencia a la tabla para poder actualizar las otras celdas
  //  private final JTable table;
    
    // Lista de equipos para inicializar el ComboBox
    private final List<Equipo> listaEquipos;

    public EquipoCellEditor( List<Equipo> equipos) {
        // Creamos el JComboBox y lo inicializamos con el tipo Equipo
        super(new JComboBox<Equipo>()); 
        //this.table = table;
        this.listaEquipos = equipos;

        // Necesitamos castear el editorComponent a JComboBox<Equipo>
        this.equipoComboBox = (JComboBox<Equipo>) getComponent();
        this.equipoComboBox.removeAllItems();
        
        // Llenar el ComboBox
        this.equipoComboBox.addItem(new Equipo()); // Placeholder
        equipos.forEach(this.equipoComboBox::addItem);

        // 🎯 Paso CLAVE: Agregar el Listener para detectar la selección y actualizar la fila
        this.equipoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Notifica que la edición ha terminado y actualiza la fila
                fireEditingStopped();
            }
        });
    }

    // Sobreescribir getValue para devolver el objeto Equipo completo
    @Override
    public Object getCellEditorValue() {
        return equipoComboBox.getSelectedItem();
    }
}