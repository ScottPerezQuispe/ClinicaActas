/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentation.view;


import application.casosdeuso.equipo.ActualizarEquipoUseCase;
import application.casosdeuso.equipo.RegistrarEquipoUseCase;
import domain.entidades.Equipo;
import infrastructure.persistencia.repositorioimpl.EquipoRepositoryImpl;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import raven.modal.listener.ModalController;



/**
 *
 * @author Scott.perez
 */

public class FrmMantenimientoProducto extends javax.swing.JPanel {
   private FrmProducto formularioPadre;
   private ModalController modalController;
   private JDialog loadingDialog;
   // 🚨 Nuevo campo para el modo edición
    private Equipo equipoEnEdicion;
    private boolean wasSuccessful = false;
    public int idEquipo;
    
   
    public FrmMantenimientoProducto() {
        initComponents();
        
        
    }
    // 🚨 Nuevo Constructor de Edición (usado por btnEditar)
    public FrmMantenimientoProducto(FrmProducto padre, Equipo equipo) {
        this();
        this.formularioPadre = padre;
        this.equipoEnEdicion = equipo; // Modo Edición
        
        // 🚨 Cargar datos al iniciar el panel
        if (equipoEnEdicion != null) {
            cargarDatosParaEdicion(equipo);
        }
    }
    // Constructor con referencia al padre (usado por FrmProducto)
   public FrmMantenimientoProducto(FrmProducto padre) {
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
    private void cargarDatosParaEdicion(Equipo equipo) {
        txt_Nombre.setText(equipo.getNombreEquipo());
        txt_Marca.setText(equipo.getMarca());
        txt_Modelo.setText(equipo.getModelo());
        txt_Cantidad.setText(String.valueOf(equipo.getCantidad()));
         idEquipo = equipo.getIdEquipo();
        // Asegúrate de cambiar el texto del botón si quieres diferenciarlo visualmente
      
    }
    
    
    public void ActualizarEquipo() {
    try {
       // 1️⃣ Obtener valores de los campos
            String nombre = txt_Nombre.getText().trim();
            String marca = txt_Marca.getText().trim();
            String modelo = txt_Modelo.getText().trim();
            String cantidadTexto = txt_Cantidad.getText().trim();

            // 2️⃣ Validaciones
            if (nombre.isEmpty() || marca.isEmpty() || modelo.isEmpty() || cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }
            
            Equipo equipo = new Equipo(idEquipo, nombre, marca, modelo, cantidad);

            // 4️⃣ Lógica de Base de Datos (SÍNCRONA)
            ActualizarEquipoUseCase registrar = new ActualizarEquipoUseCase(new EquipoRepositoryImpl());
            boolean exito = registrar.Actualizar(equipo); 

            // 5️⃣ Manejo de Resultado y Cierre
            if (exito) {
                this.wasSuccessful = true;              
            } else {
                this.wasSuccessful = false;
                JOptionPane.showMessageDialog(this, "❌ Error al registrar el equipo. La operación de base de datos falló.");
                // El modal permanece abierto.
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "️ Ocurrió un error: " + e.getMessage());
        }
    }
   public void guardarEquipo() {
    try {
       // 1️⃣ Obtener valores de los campos
            String nombre = txt_Nombre.getText().trim();
            String marca = txt_Marca.getText().trim();
            String modelo = txt_Modelo.getText().trim();
            String cantidadTexto = txt_Cantidad.getText().trim();

            // 2️⃣ Validaciones
            if (nombre.isEmpty() || marca.isEmpty() || modelo.isEmpty() || cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
                return; // 🛑 Detiene la ejecución si falla la validación
            }
            
            // 3️⃣ Crear el objeto de dominio
            Equipo nuevo = new Equipo();
            nuevo.setNombreEquipo(nombre);
            nuevo.setMarca(marca);
            nuevo.setModelo(modelo);
            nuevo.setCantidad(cantidad);

            // 4️⃣ Lógica de Base de Datos (SÍNCRONA)
            RegistrarEquipoUseCase registrar = new RegistrarEquipoUseCase(new EquipoRepositoryImpl());
            boolean exito = registrar.ejecutar(nuevo); // ¡Esto bloquea la UI brevemente!

            // 5️⃣ Manejo de Resultado y Cierre
            if (exito) {
                this.wasSuccessful = true;              
            } else {
                this.wasSuccessful = false;
                JOptionPane.showMessageDialog(this, "❌ Error al registrar el equipo. La operación de base de datos falló.");
                // El modal permanece abierto.
            }
        } catch (Exception e) {
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

        jLabel1 = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        txt_Marca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_Modelo = new javax.swing.JTextField();
        txt_Cantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("Nombre");

        txt_Marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MarcaActionPerformed(evt);
            }
        });

        jLabel2.setText("Marca");

        jLabel3.setText("Modelo");

        jLabel5.setText("Cantidad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Nombre)
                    .addComponent(txt_Marca)
                    .addComponent(txt_Modelo)
                    .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_MarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MarcaActionPerformed



    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txt_Cantidad;
    private javax.swing.JTextField txt_Marca;
    private javax.swing.JTextField txt_Modelo;
    private javax.swing.JTextField txt_Nombre;
    // End of variables declaration//GEN-END:variables
}
