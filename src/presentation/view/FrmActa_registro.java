package presentation.view;

import application.casosdeuso.empleado.ListarEmpleadoUseCase;

import domain.entidades.Empleado;
import domain.entidades.Equipo;
import infrastructure.persistencia.repositorioimpl.EmpleadoRepositoryImpl;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FrmActa_registro extends javax.swing.JPanel {
    
 
    private List<Equipo> listaEquipos;
    private DefaultTableModel tableModel;
    public FrmActa_registro() {
        initComponents();
         /*this.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Table.background");*/
         cargarEmpleadosEnComboBox();
         setupListeners();
         
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();  
      // 1. Definición del modelo (donde sobrescribes isCellEditable)
    tableModel = new DefaultTableModel(
        new Object [][] {}, // Inicialmente vacío
        new String [] {"Equipo", "Marca", "Modelo"}
    ) {
        // Lógica para definir qué columnas son editables (solo la primera)
        final boolean[] canEdit = new boolean [] {
            true, false, false 
        };

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };
    
    // 🎯 LÍNEA CRÍTICA: Asignación correcta del modelo a la tabla
    jTable1.setModel(tableModel);


    // 2. Configuración inicial
    cargarEquipos(); // Llama a tu presenter para obtener la lista de equipos
//
    // 3. Configurar el editor de celda para la Columna 0 ("Equipo")
   EquipoCellEditor equipoEditor = new EquipoCellEditor( listaEquipos);
    jTable1.getColumnModel().getColumn(0).setCellEditor(equipoEditor);

    // 4. Configurar el Action Listener para los botones
    jButton1.addActionListener(e -> agregarFila());
    jButton2.addActionListener(e -> eliminarFila());

    }

    // Asegúrate de que este método exista y devuelva el objeto
    private Equipo crearPlaceholderEquipo() {
        // Esto debería devolver el mismo objeto que agregas al inicio del EquipoCellEditor
        // Usamos el constructor de Equipo(id, nombre, marca, modelo)
        return new Equipo(0, "-- Seleccione Equipo --", "", "",1); 
    }
    private void agregarFila() {
  
        // 1. Obtener o crear el objeto placeholder
    // Asumiendo que esta función crea el objeto Equipo(0, "-- Seleccionar Equipo --", "", "")
    Equipo placeholder = crearPlaceholderEquipo(); 

    // 2. Agregar la nueva fila con el placeholder en la Columna 0
    // Columna 0 (Equipo) | Columna 1 (Marca) | Columna 2 (Modelo)
    tableModel.addRow(new Object[]{placeholder, null, null}); 
    
    // Opcional: Seleccionar la nueva fila
    int nuevaFila = tableModel.getRowCount() - 1;
    jTable1.setRowSelectionInterval(nuevaFila, nuevaFila);
    }
    
    private void eliminarFila() {
    int filaSeleccionada = jTable1.getSelectedRow();
    
    if (filaSeleccionada != -1) {
        // Elimina la fila seleccionada
        tableModel.removeRow(filaSeleccionada);
    } else {
        JOptionPane.showMessageDialog(this, 
            "Seleccione una fila para eliminar.", 
            "Atención", 
            JOptionPane.WARNING_MESSAGE);
    }
}
    
    // Método para obtener la lista de equipos (Debes implementar tu EquipoPresenter/Service)
private void cargarEquipos() {
    // ⚠️ DEBES implementar esto usando tu Arquitectura: Presenter -> Service -> Repository
    // Ejemplo mock:
    listaEquipos = List.of(
        new Equipo(1, "Laptop", "HP", "EliteBook G9",5),
        new Equipo(2, "Monitor", "Dell", "U2723QE",5)
    );
}

    // En FrmActa_registro o un método auxiliar en el Presenter
    private Empleado crearPlaceholderEmpleado() {
        // Usamos el constructor y damos valores que no representan un empleado real.
        // El ID se establece a 0 o -1 para poder validarlo después.
        // Los 'nombres' y 'apellidos' se establecen para que toString() funcione.
        return new Empleado(
            0, 
            "", 
            "-- Seleccione un Empleado --", 
            "", 
            null
        ); 
    }
        // Y el método de llenado sería:
    private void cargarEmpleadosEnComboBox() {
        ListarEmpleadoUseCase EmpleadoUseCase = new ListarEmpleadoUseCase( new EmpleadoRepositoryImpl());
        List<Empleado> empleados = EmpleadoUseCase.Listar();

        // El modelo ahora es de tipo Empleado
        //DefaultComboBoxModel<Empleado> model = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<Empleado> model = new DefaultComboBoxModel<>();
        // Opcional: Agregar un Empleado "dummy" para la selección inicial (con ID -1, por ejemplo)

        // 🎯 Paso CLAVE: Añadir el marcador de posición primero
        model.addElement(crearPlaceholderEmpleado());
        for (Empleado empleado : empleados) {
            model.addElement(empleado); // Agregamos el objeto Empleado directamente
        }

        cboEmpleado.setModel(model);
        // Para que muestre el nombre, la clase Empleado DEBE SOBREESCRIBIR el método toString()
    }
    
    // En FrmActa_registro

    private void setupListeners() {
        // Detecta cuando el usuario cambia la selección
        cboEmpleado.addActionListener(e -> {
            mostrarAreaEmpleadoSeleccionado();
        });
    }
    private void mostrarAreaEmpleadoSeleccionado() {
        // 1. Obtener el objeto Empleado seleccionado del ComboBox
        Empleado empleadoSeleccionado = (Empleado) cboEmpleado.getSelectedItem();

        // 2. Validar si es un empleado real (no el placeholder)
        if (empleadoSeleccionado != null && empleadoSeleccionado.getIdEmpleado() != 0) {

            // Asumiendo que el campo para el Área en el JTextArea es jTextArea1.
            // Y que el Area tiene un método getNombre()

            String nombreArea = empleadoSeleccionado.getArea().getNombre();

            // 3. Establecer el texto en el JTextArea (o el campo de texto que uses para el área)
            txtArea.setText(nombreArea);

            // Opcional: Podrías usar un JTextField/JLabel para el Área en lugar de un JTextArea si solo es una línea.

        } else {
            // Si se selecciona el placeholder ("-- Seleccione..."), limpiamos el área de texto
            txtArea.setText(""); 
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        cboEmpleado = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtArea = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();

        setPreferredSize(new java.awt.Dimension(1190, 613));
        setRequestFocusEnabled(false);

        jButton3.setText("Cancelar");

        jButton4.setText("Guardar");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton2.setText("Eliminar Fila");

        jButton1.setText("Agregar Fila");

        jLabel6.setText("Comentario :");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Equipo", "Marca", "Modelo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel3.setText("Empleado :");

        jLabel2.setText("Tipo de Acta :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Acta");

        jRadioButton1.setText("Entrega");

        jRadioButton2.setText("Recojo");

        jLabel5.setText("Fecha :");

        jLabel4.setText("Area : ");

        txtArea.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(354, 354, 354)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 666, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel6)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1178, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel4)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Empleado> cboEmpleado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JFormattedTextField txtArea;
    // End of variables declaration//GEN-END:variables
}
