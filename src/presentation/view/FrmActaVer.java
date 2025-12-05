package presentation.view;

import application.casosdeuso.acta.AprobarActaUseCase;
import application.casosdeuso.acta.EditarActaUseCase;
import application.casosdeuso.acta.GenerarActaPDFUseCase;
import application.casosdeuso.acta.RechazarActaUseCase;
import domain.entidades.Acta;
import domain.entidades.DetalleActa;
import domain.entidades.Usuario;
import infrastructure.persistencia.repositorioimpl.ActaPdfGeneratorImpl;
import infrastructure.persistencia.repositorioimpl.ActaRepositoryImpl;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static presentation.view.FrmActa_registro.parentFrame;

public class FrmActaVer extends javax.swing.JPanel {

    public static Main parentFrame;
    private final Usuario usuarioLogueado;
    private DefaultTableModel tableModel;
    private final int idActaAEditar;
    private Acta actaOriginal;

    public FrmActaVer(Main parentFrame,Usuario usuario, int idActa) {
        this.parentFrame = parentFrame;
        this.usuarioLogueado = usuario;
        this.idActaAEditar = idActa;
        initComponents();
        configurarTablaYListeners();
        cargarDatosActa(idActa);
        
        if (usuario.getNombreRol().equals(("Soporte"))){
          
            btnRechazar.setVisible(false);
            btnAprobar.setVisible(false);
        }
    }

    /*EDITAR*/
    private void cargarDatosActa(int idActa) {

        EditarActaUseCase consultarUseCase = new EditarActaUseCase(new ActaRepositoryImpl());
        actaOriginal = consultarUseCase.buscarPorId(idActa); 
        actaOriginal.setIdActa(idActa);
        lblTipo.setText(actaOriginal.getTipoActa());
        lblEmpleado.setText(actaOriginal.getEmpleadoNombres());
        lblRegistradoPor.setText(actaOriginal.getRegistradorUsuario());
        lblFechaRegistro.setText(actaOriginal.getFechaSoporte());
        txtFecha.setText(actaOriginal.getFechaRegistro());
        txtArea.setText(actaOriginal.getEmpleadoArea());
        lblAprobadoPor.setText(actaOriginal.getAprobadorUsuario());
        lblFechaAprobado.setText(actaOriginal.getFechaCoordinador());
        txtComentario.setText(actaOriginal.getComentario());

        llenarTablaEquipos(actaOriginal.getDetalles());
         btnDescargar.setVisible(false);
        if (actaOriginal.getIdEstado()==2){
          
            btnRechazar.setVisible(false);
            btnAprobar.setVisible(false);
        }
         if (actaOriginal.getIdEstado()==2){
            btnDescargar.setVisible(true);
        }
        
        if (actaOriginal.getTipoActa().equals("Recojo")){
            LabelAprobado.setVisible(false);
            LabelFechaAprobado.setVisible(false);
        }
        
    }

    private void llenarTablaEquipos(List<DetalleActa> detalles) {
       
        tableModel.setRowCount(0);

        for (DetalleActa detalle : detalles) {
            tableModel.addRow(new Object[]{
                detalle.getEquipo().getNombreEquipo(),
                detalle.getEquipo().getMarca(),
                detalle.getEquipo().getModelo()
            });
        }
    }

    private void configurarTablaYListeners() {

        if (jTable1.getModel() instanceof DefaultTableModel defaultTableModel) {
            this.tableModel = defaultTableModel;
        } else {

            String[] columns = {"Equipo", "Marca", "Modelo"};
            this.tableModel = new DefaultTableModel(columns, 0);
            jTable1.setModel(this.tableModel);
        }

        btnAprobar.addActionListener(e -> aprobarActa());
        btnRechazar.addActionListener(e -> rechazarActa());
        btnCancelar.addActionListener(e -> cancelarActa());
        btnDescargar.addActionListener(e -> DescargarPDF());
    }

    private void DescargarPDF(){
         try {
       // Acta acta = obtenerActaSeleccionada(); // tú lo construyes aquí

        GenerarActaPDFUseCase useCase = new GenerarActaPDFUseCase(
            new ActaPdfGeneratorImpl()
        );

        String ruta = useCase.ejecutar(this.actaOriginal);

        JOptionPane.showMessageDialog(this, 
            "PDF generado correctamente:\n" + ruta);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }
    private void cancelarActa(){
        FrmBandejaActa panel = new FrmBandejaActa( parentFrame, usuarioLogueado);
        this.parentFrame.showPanel(panel);    
    }
    
    private void aprobarActa() {
       
        try {
            // Confirmación
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea APROBAR esta Acta?",
                    "Confirmar Aprobación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                int idActa = this.idActaAEditar;
                int idCoordinador = this.usuarioLogueado.getIdUsuario(); 

                AprobarActaUseCase aprobarUseCase = new AprobarActaUseCase(new ActaRepositoryImpl());
                boolean exito = aprobarUseCase.aprobar(idActa, idCoordinador);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Acta APROBADA exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                  
                    FrmBandejaActa panel = new FrmBandejaActa( parentFrame, usuarioLogueado);
                    this.parentFrame.showPanel(panel);     
                    //cargarDatosActa(idActa);
                } else {
                    JOptionPane.showMessageDialog(this, "Fallo al aprobar el Acta. Contacte a soporte.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al aprobar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void rechazarActa() {
       
        try {
           
            int idActa = this.idActaAEditar;
            int idCoordinador = this.usuarioLogueado.getIdUsuario();
            
            RechazarActaUseCase rechazarUseCase = new RechazarActaUseCase(new ActaRepositoryImpl());
            boolean exito = rechazarUseCase.ejecutar(idActa, idCoordinador);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Acta RECHAZADA exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
               
            } else {
                JOptionPane.showMessageDialog(this, "Fallo al rechazar el Acta. Contacte a soporte.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al rechazar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnAprobar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtArea = new javax.swing.JFormattedTextField();
        txtFecha = new javax.swing.JFormattedTextField();
        lblTipo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        lblRegistradoPor = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblFechaRegistro = new javax.swing.JLabel();
        LabelAprobado = new javax.swing.JLabel();
        LabelFechaAprobado = new javax.swing.JLabel();
        lblAprobadoPor = new javax.swing.JLabel();
        lblFechaAprobado = new javax.swing.JLabel();
        btnRechazar = new javax.swing.JButton();
        btnDescargar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1190, 613));
        setRequestFocusEnabled(false);

        btnCancelar.setText("Cancelar");

        btnAprobar.setText("Aprobar");
        btnAprobar.setToolTipText("");

        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        txtComentario.setEnabled(false);
        jScrollPane2.setViewportView(txtComentario);

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
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel3.setText("Empleado :");

        jLabel2.setText("Tipo de Acta :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Acta");

        jLabel5.setText("Fecha:");

        jLabel4.setText("Area: ");

        txtArea.setEnabled(false);

        txtFecha.setEnabled(false);

        lblTipo.setText("lblTipo");
        lblTipo.setToolTipText("");

        jLabel7.setText("Registrador por:");

        lblEmpleado.setText("lblEmpleado");
        lblEmpleado.setToolTipText("");

        lblRegistradoPor.setText("registradopor");

        jLabel8.setText("Fecha registro:");

        lblFechaRegistro.setText("fecharegistro");

        LabelAprobado.setText("Aprobado por:");

        LabelFechaAprobado.setText("Fecha Aprobado:");

        lblAprobadoPor.setText("Aprobado");
        lblAprobadoPor.setToolTipText("");

        lblFechaAprobado.setText("Fecha");

        btnRechazar.setText("Rechazar");

        btnDescargar.setText("Descargar a PDF");

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
                                .addComponent(jLabel6)))
                        .addGap(0, 791, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblRegistradoPor)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(LabelAprobado))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblTipo)
                                                    .addComponent(lblEmpleado))
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel4))))
                                        .addGap(43, 43, 43))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFechaRegistro)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
                                        .addComponent(LabelFechaAprobado, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblAprobadoPor)
                                    .addComponent(lblFechaAprobado))
                                .addGap(124, 124, 124)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDescargar)
                .addGap(18, 18, 18)
                .addComponent(btnAprobar)
                .addGap(24, 24, 24)
                .addComponent(btnRechazar)
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
                    .addComponent(jLabel4)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(lblEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblRegistradoPor)
                    .addComponent(LabelAprobado)
                    .addComponent(lblAprobadoPor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblFechaRegistro)
                    .addComponent(LabelFechaAprobado)
                    .addComponent(lblFechaAprobado))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAprobar)
                    .addComponent(btnCancelar)
                    .addComponent(btnRechazar)
                    .addComponent(btnDescargar))
                .addContainerGap(156, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelAprobado;
    private javax.swing.JLabel LabelFechaAprobado;
    private javax.swing.JButton btnAprobar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDescargar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAprobadoPor;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblFechaAprobado;
    private javax.swing.JLabel lblFechaRegistro;
    private javax.swing.JLabel lblRegistradoPor;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JFormattedTextField txtArea;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JFormattedTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
