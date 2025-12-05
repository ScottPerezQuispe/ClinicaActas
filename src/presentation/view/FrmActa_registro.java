package presentation.view;

import application.casosdeuso.acta.RegistrarActaUseCase;
import application.casosdeuso.empleado.ListarEmpleadoUseCase;
import application.casosdeuso.equipo.ListarEquipoUseCase;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import domain.entidades.Acta;
import domain.entidades.DetalleActa;

import domain.entidades.Empleado;
import domain.entidades.Equipo;
import domain.entidades.Usuario;
import infrastructure.persistencia.repositorioimpl.ActaRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EmpleadoRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EquipoRepositoryImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import static presentation.view.FrmActaVer.parentFrame;

public class FrmActa_registro extends javax.swing.JPanel {

    public static Main parentFrame;
    private Usuario usuarioLogueado;
    private DateChooser chDate = new DateChooser();
    private DefaultTableModel model;
    private List<Equipo> listaEquipos = new ArrayList<>();
    ;
    private DefaultTableModel tableModel;

    public FrmActa_registro(Main parentFrame, Usuario usuario) {
        this.parentFrame = parentFrame;
        this.usuarioLogueado = usuario;
        initComponents();
        cargarEmpleadosEnComboBox();
        configurarTablaYListeners();
        setupListeners();

        MostrarCalendario();
        inicializarComponentes();
        inicializarEditorTabla();
    }

    private void inicializarComponentes() {
        // Asumiendo que tus radio buttons se llaman jRadioButtonEntrega y jRadioButtonRecojo

        ButtonGroup tipoActaGroup = new ButtonGroup();

        // 1. Agregar los radio buttons al grupo
        tipoActaGroup.add(jRadioButtonEntrega);
        tipoActaGroup.add(jRadioButtonRecojo);

        // Opcional: Seleccionar uno por defecto
        jRadioButtonEntrega.setSelected(true);
    }

    private void configurarTablaYListeners() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        // 1. Definición del modelo (donde sobrescribes isCellEditable)
        tableModel = new DefaultTableModel(
                new Object[][]{}, // Inicialmente vacío
                new String[]{"Equipo", "Marca", "Modelo"}
        ) {
            // Lógica para definir qué columnas son editables (solo la primera)
            final boolean[] canEdit = new boolean[]{
                true, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        // 🎯 LÍNEA CRÍTICA: Asignación correcta del modelo a la tabla
        jTable1.setModel(tableModel);

        //  cargarEquipos(0);
        //
        // 3. Configurar el editor de celda para la Columna 0 ("Equipo")
        // EquipoCellEditor equipoEditor = new EquipoCellEditor(jTable1, listaEquipos);
        //jTable1.getColumnModel().getColumn(0).setCellEditor(equipoEditor);
        // 4. Configurar el Action Listener para los botones
        jButton1.addActionListener(e -> agregarFila());
        jButton2.addActionListener(e -> eliminarFila());
        btnGuardar.addActionListener(e -> guardarActa());
        btnCancelar.addActionListener(e -> cancelarActa());
    }
    private void cancelarActa(){
        FrmBandejaActa panel = new FrmBandejaActa( parentFrame, usuarioLogueado);
        this.parentFrame.showPanel(panel);    
    }
    // Asegúrate de que este método exista y devuelva el objeto
    private Equipo crearPlaceholderEquipo() {
        return new Equipo(0, "-- Seleccione Equipo --", "", "", 1);
    }

    private void inicializarEditorTabla() {
        jRadioButtonEntrega.setSelected(true);
        recargarEquiposPorTipo();
        // 1. Inicialmente, configuramos el editor con una lista vacía o nula.
        // Esto asegura que la tabla tenga un editor válido al inicio.
        EquipoCellEditor equipoEditor = new EquipoCellEditor(jTable1, listaEquipos);
        jTable1.getColumnModel().getColumn(0).setCellEditor(equipoEditor);

        // 2. 🎯 Simular un cambio en el empleado para forzar la carga dinámica
        // Esto llamará a recargarEquiposPorTipo(), que sí respeta la condición del radio button.
        // mostrarAreaEmpleadoSeleccionado(); 
    }

    private void agregarFila() {

        Equipo placeholder = crearPlaceholderEquipo();

        // 2. Agregar la nueva fila con el placeholder en la Columna 0
        // Columna 0 (Equipo) | Columna 1 (Marca) | Columna 2 (Modelo)
        tableModel.addRow(new Object[]{placeholder, null, null});

        // Opcional: Seleccionar la nueva fila
        int nuevaFila = tableModel.getRowCount() - 1;
        jTable1.setRowSelectionInterval(nuevaFila, nuevaFila);
        System.out.println("nuevaFila  " + nuevaFila + " " + tableModel.getRowCount());
    }

private void eliminarFila() {
    // 1. **Paso CLAVE:** Forzar el fin de cualquier edición en curso.
    if (jTable1.isEditing()) {
        // Detener la edición actual antes de modificar el modelo.
        // Si el valor editado es válido, se guardará. Si no, se cancela.
        if (jTable1.getCellEditor().stopCellEditing()) {
            // Edición detenida exitosamente
        } else {
            jTable1.getCellEditor().cancelCellEditing();
        }
    }
    
    // 2. Proceso de eliminación de fila (Tu lógica original)
    int filaSeleccionada = jTable1.getSelectedRow();

    if (filaSeleccionada != -1) {
        // Elimina la fila seleccionada
        tableModel.removeRow(filaSeleccionada);
        // Opcional: Si eliminas la última fila, asegúrate de que no haya selección residual.
        if (tableModel.getRowCount() > 0 && filaSeleccionada < tableModel.getRowCount()) {
             jTable1.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
        }
    } else {
        JOptionPane.showMessageDialog(this,
                "Seleccione una fila para eliminar.",
                "Atención",
                JOptionPane.WARNING_MESSAGE);
    }
    System.out.println("filaSeleccionada  " + filaSeleccionada + " " + tableModel.getRowCount());
}

    // Método para obtener la lista de equipos (Debes implementar tu EquipoPresenter/Service)
    private void cargarEquipos(int IdEmpleado) {
        ListarEquipoUseCase listaUseCase = new ListarEquipoUseCase(new EquipoRepositoryImpl());
        listaEquipos = listaUseCase.ListarCombo(IdEmpleado);
    }

    private void cargarEquiposEmpleado(int IdEmpleado) {
        ListarEquipoUseCase listaUseCase = new ListarEquipoUseCase(new EquipoRepositoryImpl());
        listaEquipos = listaUseCase.ListarEquiposPorEmpleado(IdEmpleado);
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
        ListarEmpleadoUseCase EmpleadoUseCase = new ListarEmpleadoUseCase(new EmpleadoRepositoryImpl());
        List<Empleado> empleados = EmpleadoUseCase.ListarCombo();

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

    private void recargarEquiposPorTipo() {
        Empleado empleadoSeleccionado = (Empleado) cboEmpleado.getSelectedItem();

        if (empleadoSeleccionado == null || empleadoSeleccionado.getIdEmpleado() == 0) {
            return;
        }

        if (jRadioButtonEntrega.isSelected()) {
            cargarEquipos(empleadoSeleccionado.getIdEmpleado());
        } else {
            cargarEquiposEmpleado(empleadoSeleccionado.getIdEmpleado());
        }

        // ACTUALIZA EL EDITOR DE LA TABLA
        EquipoCellEditor equipoEditor = new EquipoCellEditor(jTable1, listaEquipos);
        jTable1.getColumnModel().getColumn(0).setCellEditor(equipoEditor);
    }

    private void limpiarTablaYEmpleadoAlCambiarTipo() {

        // 1. Limpiar la tabla (eliminar todas las filas)
        tableModel.setRowCount(0);

        // 2. Limpiar la selección del Empleado
        // La opción 0 es tu placeholder: "-- Seleccione un Empleado --"
        cboEmpleado.setSelectedIndex(0);

        // 3. Limpiar el campo de Área
        txtArea.setText("");

        // 4. Limpiar la lista de equipos disponible (opcional, ya se recargará en recargarEquiposPorTipo)
        // Pero lo incluimos por seguridad si el editor pudiera acceder a ella antes de la recarga.
        listaEquipos.clear();
    }

    private void setupListeners() {
        // Detecta cuando el usuario cambia la selección
        cboEmpleado.addActionListener(e -> {
            mostrarAreaEmpleadoSeleccionado();
        });

        // 🎯 MODIFICACIÓN DE LOS LISTENERS DE RADIO BUTTONS
        jRadioButtonEntrega.addActionListener(e -> {
            limpiarTablaYEmpleadoAlCambiarTipo();
            // Después de limpiar, recarga el editor con la nueva lista 
            // (esto puede disparar una carga si ya hay un empleado seleccionado)
            recargarEquiposPorTipo();
        });

        jRadioButtonRecojo.addActionListener(e -> {
            limpiarTablaYEmpleadoAlCambiarTipo();
            // Después de limpiar, recarga el editor con la nueva lista
            recargarEquiposPorTipo();
        });

        // 🎯 Implementación del Listener para la TABLA
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Solo procesamos actualización de DATOS (UPDATE)
                // en la columna 0 (Equipo)
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {

                    int row = e.getFirstRow();

                    // 🎯 1. VERIFICACIÓN CRÍTICA: Asegurar que la fila aún existe
                    if (row < 0 || row >= tableModel.getRowCount()) {
                        // Si la fila es inválida (ej. ya eliminada), simplemente salimos.
                        return;
                    }
                    // Obtener el objeto Equipo que se acaba de guardar en la Columna 0
                    Object value = tableModel.getValueAt(row, 0);

                    if (value instanceof Equipo) {
                        Equipo equipoSeleccionado = (Equipo) value;

                        // Validar si es un equipo real (asumiendo ID 0 es el placeholder)
                        if (equipoSeleccionado.getIdEquipo() != 0) {

                            // 🎯 ACTUALIZACIÓN DE CELDAS
                            tableModel.setValueAt(equipoSeleccionado.getMarca(), row, 1); // Marca
                            tableModel.setValueAt(equipoSeleccionado.getModelo(), row, 2); // Modelo

                        } else {
                            // Limpiar celdas si se selecciona el placeholder
                            tableModel.setValueAt(null, row, 1);
                            tableModel.setValueAt(null, row, 2);
                        }
                    }
                }
            }
        });

    }

    private void mostrarAreaEmpleadoSeleccionado() {
        // 1. Obtener el objeto Empleado seleccionado del ComboBox
        Empleado empleadoSeleccionado = (Empleado) cboEmpleado.getSelectedItem();

        // 2. Validar si es un empleado real (no el placeholder)
        if (empleadoSeleccionado != null && empleadoSeleccionado.getIdEmpleado() != 0) {
            // --- 🎯 ACCIÓN CLAVE 1: Limpiar la tabla de equipos anterior ---
            // Vaciamos todas las filas del modelo
            tableModel.setRowCount(0);
            // Asumiendo que el campo para el Área en el JTextArea es jTextArea1.
            // Y que el Area tiene un método getNombre()
            String nombreArea = empleadoSeleccionado.getArea().getNombre();

            // 3. Establecer el texto en el JTextArea (o el campo de texto que uses para el área)
            txtArea.setText(nombreArea);

            // 2. Configuración inicial
            recargarEquiposPorTipo();

            // Opcional: Podrías usar un JTextField/JLabel para el Área en lugar de un JTextArea si solo es una línea.
        } else {
            // Si se selecciona el placeholder ("-- Seleccione..."), limpiamos el área de texto
            txtArea.setText("");
            tableModel.setRowCount(0); // Limpiar tabla si es el placeholder.
        }
    }

    private void MostrarCalendario() {

        chDate.setTextField(txtFecha);

        // 1. CONFIGURACIÓN EN MODO FECHA ÚNICA (Correcto)
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);

        chDate.setLabelCurrentDayVisible(false);
        chDate.setDateFormat(new SimpleDateFormat("dd-MMMM-yyyy"));

        // Configuración del Listener (Asumo que ya lo adaptaste a dateSelected)
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
            // @Override
            public void dateSelected(Date date, DateChooserAction action) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String selectedDate = df.format(date);
                //  loadData("select * from invoice where Date = '" + selectedDate + "'");
            }
            // Eliminamos la sobreescritura de dateBetweenChanged
        });

        Date today = new Date();
        chDate.setSelectedDate(today);

    }

    private boolean validarFormulario() {
        // 1. Validar Empleado
        Empleado empleado = (Empleado) cboEmpleado.getSelectedItem();
        if (empleado == null || empleado.getIdEmpleado() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un empleado.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            cboEmpleado.requestFocusInWindow();
            return false;
        }

        // 2. Validar Equipos en la tabla (debe haber al menos un equipo no placeholder)
        boolean hayEquiposValidos = false;
        if (tableModel.getRowCount() > 0) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object equipoObj = tableModel.getValueAt(i, 0);
                if (equipoObj instanceof Equipo) {
                    Equipo equipo = (Equipo) equipoObj;
                    if (equipo.getIdEquipo() != 0) {
                        hayEquiposValidos = true;
                        break;
                    }
                }
            }
        }

        if (!hayEquiposValidos) {
            JOptionPane.showMessageDialog(this,
                    "Debe agregar y seleccionar al menos un equipo válido en la tabla.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            jTable1.requestFocusInWindow();
            return false;
        }

        // 3. Validar Fecha (por si el DateChooser falla o el campo es modificado)
        if (txtFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El campo Fecha no puede estar vacío.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            txtFecha.requestFocusInWindow();
            return false;
        }

        return true;
    }

    private void guardarActa() {
        // 1. VALIDACIÓN
        if (!validarFormulario()) {
            return; // Detiene el proceso si la validación falla
        }

        // 2. EXTRACCIÓN DE DATOS DE LA CABECERA (ACTA)
        try {
            // Tipo de Acta
            String tipoActa = jRadioButtonEntrega.isSelected() ? "Entrega" : "Recojo";

            // Empleado Seleccionado
            Empleado empleado = (Empleado) cboEmpleado.getSelectedItem();

            // Fecha (Conversión de String a Date)
            // Asumiendo que el formato del DateChooser es el esperado (ej: "25-05-2024")
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy"); // Formato de lectura
            Date fechaActa = df.parse(txtFecha.getText());

            // Comentario
            String comentario = txtComentario.getText().trim();

            // 4. EXTRACCIÓN DE DETALLES (ACTADETALLE)
            List<DetalleActa> detalles = new ArrayList<>();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Equipo equipo = (Equipo) tableModel.getValueAt(i, 0);

                // Validar que no sea el placeholder (ID 0)
                if (equipo.getIdEquipo() != 0) {

                    DetalleActa detalle = new DetalleActa();

                    detalle.setIdEquipo(equipo.getIdEquipo());

                    detalles.add(detalle);

                }
            }

            // --- 3. CREACIÓN DE LA ENTIDAD ACTA (Dominio - Simulación) ---
            Acta nuevaActa = new Acta(
                    fechaActa,
                    tipoActa,
                    empleado.getIdEmpleado(),
                    this.usuarioLogueado.getIdUsuario(),
                    comentario,
                    detalles
            );
            // Si llegamos aquí, sabemos que la lista 'detalles' no está vacía gracias a validarFormulario()
            // --- 5. REGISTRO (USO DEL CASO DE USO - Simulación) ---
            // Ejemplo de uso real:
            RegistrarActaUseCase registrarUseCase = new RegistrarActaUseCase(new ActaRepositoryImpl());
            boolean exito = registrarUseCase.Registrar(nuevaActa);

            if (exito) {
                FrmBandejaActa panel = new FrmBandejaActa(parentFrame, usuarioLogueado);
                this.parentFrame.showPanel(panel);
                JOptionPane.showMessageDialog(this,
                        "Acta registrada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar el Acta. La transacción fue revertida.",
                        "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }
            /*
            // Simulación de éxito:
            JOptionPane.showMessageDialog(this,
                    "Acta simulada a registrar:\n"
                    + "Tipo: " + tipoActa + "\n"
                    + "Empleado: " + empleado.getNombres() + "\n"
                    + "Fecha: " + df.format(fechaActa) + "\n"
                    + "Equipos: " + detalles.size(),
                    "Simulación de Guardado Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);*/

            // TODO: Agregar el método resetearFormulario() para limpiar los campos
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Error interno de formato de fecha. Por favor, revise el campo Fecha.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error inesperado al guardar el acta: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jRadioButtonEntrega = new javax.swing.JRadioButton();
        jRadioButtonRecojo = new javax.swing.JRadioButton();
        cboEmpleado = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtArea = new javax.swing.JFormattedTextField();
        txtFecha = new javax.swing.JFormattedTextField();

        setPreferredSize(new java.awt.Dimension(1190, 613));
        setRequestFocusEnabled(false);

        btnCancelar.setText("Cancelar");

        btnGuardar.setText("Guardar");

        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        jScrollPane2.setViewportView(txtComentario);

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

        jRadioButtonEntrega.setText("Entrega");

        jRadioButtonRecojo.setText("Recojo");
        jRadioButtonRecojo.setToolTipText("");
        jRadioButtonRecojo.setActionCommand("DEVOLUCIÓN");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(354, 354, 354)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))
                                    .addComponent(jLabel6))))
                        .addGap(0, 792, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButtonEntrega)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButtonRecojo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4)))
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(124, 124, 124))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButtonEntrega)
                    .addComponent(jRadioButtonRecojo)
                    .addComponent(jLabel4)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<Empleado> cboEmpleado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JRadioButton jRadioButtonEntrega;
    private javax.swing.JRadioButton jRadioButtonRecojo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField txtArea;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JFormattedTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
