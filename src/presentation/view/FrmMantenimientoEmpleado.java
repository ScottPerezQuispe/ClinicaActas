package presentation.view;

import application.casosdeuso.area.ListarAreaUseCase;
import application.casosdeuso.empleado.ActualizarEmpleadoUseCase;
import application.casosdeuso.empleado.ListarEmpleadoUseCase;
import application.casosdeuso.empleado.RegistrarEmpleadoUseCase;
import domain.entidades.Area;
import domain.entidades.Empleado;
import infrastructure.persistencia.repositorioimpl.AreaRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EmpleadoRepositoryImpl;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import raven.modal.listener.ModalController;

public class FrmMantenimientoEmpleado extends javax.swing.JPanel {
    
    private FrmUsuario formularioPadre;
    private ModalController modalController;
    private JDialog loadingDialog;
   // 🚨 Nuevo campo para el modo edición
    private Empleado empleadoEnEdicion;
    private boolean wasSuccessful = false;
    public int idEmpleado;
    private Area areaSeleccionada;

    
    
    public FrmMantenimientoEmpleado() {
        initComponents();
        cargarAreasEnComboBox(); // 🔹 llenar combo al abrir
    }
    
    // 🚨 Nuevo Constructor de Edición (usado por btnEditar)
    public FrmMantenimientoEmpleado(FrmUsuario padre, Empleado empleado) {
        this();
        this.formularioPadre = padre;
        cargarAreasEnComboBox(); // <-- combo empleado
        this.empleadoEnEdicion = empleado; // Modo Edición
        
        // 🚨 Cargar datos al iniciar el panel
        if (empleadoEnEdicion != null) {
            cargarDatosParaEdicion(empleado);
        }
    }
    
    // Constructor con referencia al padre (usado por FrmUsuario)
   public FrmMantenimientoEmpleado(FrmUsuario padre) {
    this();
    this.formularioPadre = padre;

    }
   // 🚨 CAMBIO 2: Cambiar el tipo de argumento en el setter
   public void setModalController(ModalController mc) {
        this.modalController = mc;
    }
   public boolean wasSuccessful() {
        return wasSuccessful;
    }

   // Método para cargar datos en los campos de texto
    private void cargarDatosParaEdicion(Empleado empleado) {
        txt_Nombre.setText(empleado.getNombres());
        txt_Apellido.setText(empleado.getApellidos());
        txt_DNI.setText(empleado.getDni());
        idEmpleado = empleado.getIdEmpleado();

        // 🔹 Guardar el área del empleado
        if (empleado.getArea() != null) {
            areaSeleccionada = empleado.getArea();
            cb_Area.setSelectedItem(areaSeleccionada);
        }
    }
    
    private Area crearPlaceholderArea() {
        // ID 0 o -1 como valor inválido para validar después
        return new Area(0,"-- Seleccione un Área --",true);
    }

    
    private void cargarAreasEnComboBox() {
        ListarAreaUseCase areaUseCase = new ListarAreaUseCase(new AreaRepositoryImpl());
        List<Area> areas = areaUseCase.listar();

        DefaultComboBoxModel<Area> model = new DefaultComboBoxModel<>();
        model.addElement(crearPlaceholderArea());

        for (Area area : areas) {
            model.addElement(area);
        }

        cb_Area.setModel(model);
    }

    
    public void ActualizarEmpleado() {
    try {
       // 1️⃣ Obtener valores de los campos
            String nombre = txt_Nombre.getText().trim();
            String apellido = txt_Apellido.getText().trim();
            String dni = txt_DNI.getText().trim();
            Area area = (Area) cb_Area.getSelectedItem();
            
            // 2️⃣ Validaciones
            
            if (area.getIdArea() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un área válida.");
                return;
            }
            

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }
            
            Empleado empleado = new Empleado(idEmpleado, dni, nombre, apellido, area);

            // 4️⃣ Lógica de Base de Datos (SÍNCRONA)
            ActualizarEmpleadoUseCase registrar = new ActualizarEmpleadoUseCase(new EmpleadoRepositoryImpl());
            registrar.Actualizar(empleado); 
            wasSuccessful = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Ocurrió un error: " + e.getMessage());
        }
    }
    
    public void guardarEmpleado() {
    try {
       // 1️⃣ Obtener valores de los campos
            String nombre = txt_Nombre.getText().trim();
            String apellido = txt_Apellido.getText().trim();
            String dni = txt_DNI.getText().trim();
            Area area = (Area) cb_Area.getSelectedItem();

            // 2️⃣ Validaciones
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }

            
            // 3️⃣ Crear el objeto de dominio
            Empleado nuevo = new Empleado();
            nuevo.setNombres(nombre);
            nuevo.setApellidos(apellido);
            nuevo.setDni(dni);
            nuevo.setArea(area);

            // 4️⃣ Lógica de Base de Datos (SÍNCRONA)
            RegistrarEmpleadoUseCase registrar = new RegistrarEmpleadoUseCase(new EmpleadoRepositoryImpl());
            registrar.Registrar(nuevo); // ¡Esto bloquea la UI brevemente!
            wasSuccessful = true;

        } catch (Exception e) {
            wasSuccessful = true;
            JOptionPane.showMessageDialog(this, "⚠️ Ocurrió un error: " + e.getMessage());
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

        txt_Nombre = new javax.swing.JTextField();
        txt_Apellido = new javax.swing.JTextField();
        txt_DNI = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cb_Area = new javax.swing.JComboBox<>();

        txt_DNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_DNIKeyTyped(evt);
            }
        });

        jLabel1.setText("Nombre :");

        jLabel2.setText("Apellido :");

        jLabel3.setText("DNI :");

        jLabel4.setText("Area :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(txt_Apellido)
                    .addComponent(txt_DNI)
                    .addComponent(cb_Area, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_DNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cb_Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_DNIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DNIKeyTyped
        char c = evt.getKeyChar();

        // Solo permitir números
        if (!Character.isDigit(c)) {
            evt.consume(); // Ignora el carácter
        }

        // Limitar longitud (opcional, por ejemplo 8 dígitos)
        if (txt_DNI.getText().length() >= 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_DNIKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Area> cb_Area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txt_Apellido;
    private javax.swing.JTextField txt_DNI;
    private javax.swing.JTextField txt_Nombre;
    // End of variables declaration//GEN-END:variables
}
