/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view;
import domain.entidades.Equipo;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor; // <--- DEBE ESTAR IMPORTADO
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author Scott.perez
 */
public class EquipoCellEditor extends DefaultCellEditor {
    
    // El JComboBox real que se mostrará
    private final JComboBox<Equipo> equipoComboBox;
   
    // Lista de equipos para inicializar el ComboBox
    private final List<Equipo> listaEquipos;
    private int currentRow; // Fila que se está editando actualmente
    private final JTable table;
    
    public EquipoCellEditor( JTable table ,List<Equipo> equipos) {
        // Creamos el JComboBox y lo inicializamos con el tipo Equipo
        super(new JComboBox<Equipo>()); 
        this.table = table;
        this.listaEquipos = equipos;

        // Necesitamos castear el editorComponent a JComboBox<Equipo>
        this.equipoComboBox = (JComboBox<Equipo>) getComponent();
        this.equipoComboBox.removeAllItems();
        
       // this.equipoComboBox.addItem(new Equipo(0, "-- Seleccionar --", "", "", 1));
        // Llenar el ComboBox
        //this.equipoComboBox.addItem(new Equipo()); // Placeholder
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

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Almacenar la fila actual para excluirla del filtrado
        this.currentRow = row; 
        
        // 1. Limpiar y rellenar el ComboBox con los equipos disponibles (filtrados)
        updateComboBoxItems(); 
        
        // 2. Si la celda ya tiene un valor (un objeto Equipo), lo seleccionamos
        if (value instanceof Equipo) {
             equipoComboBox.setSelectedItem(value);
        } else {
             // Si no hay valor, seleccionamos el placeholder
             equipoComboBox.setSelectedIndex(0);
        }

        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
    
    // 🎯 Método CLAVE 2: Lógica de Filtrado Activo
    private void updateComboBoxItems() {
        equipoComboBox.removeAllItems();
        
        // Obtener la lista de equipos ya usados en OTRAS filas
        List<Equipo> equiposUsados = getSelectedEquipos(table, currentRow);
        
        // 1. Añadir el marcador de posición
        equipoComboBox.addItem(new Equipo(0, "-- Seleccionar --", "", "", 1));

        // 2. Recorrer la lista total y agregar solo los NO usados
        for (Equipo equipo : listaEquipos) {
            // Comprobamos si el equipo ya está en uso
            if (!equiposUsados.contains(equipo)) {
                equipoComboBox.addItem(equipo);
            }
        }
    }
    
    // 🎯 Método CLAVE 3: Recorre la tabla para ver qué equipos ya se seleccionaron
    private List<Equipo> getSelectedEquipos(JTable table, int currentRow) {
        List<Equipo> usados = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
            // Excluir la fila que estamos editando
            if (i != currentRow) { 
                Object item = model.getValueAt(i, 0); // Columna 0 es Equipo
                if (item instanceof Equipo) {
                    Equipo equipo = (Equipo) item;
                    // Excluir el marcador de posición de la lista de usados
                    if (equipo.getIdEquipo() != 0) {
                        usados.add(equipo);
                    }
                }
            }
        }
        return usados;
    }
    // Sobreescribir getValue para devolver el objeto Equipo completo
    @Override
    public Object getCellEditorValue() {
        return equipoComboBox.getSelectedItem();
    }
}