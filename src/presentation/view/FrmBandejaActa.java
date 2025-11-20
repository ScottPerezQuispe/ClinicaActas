package presentation.view;

import application.casosdeuso.acta.ListarActaUseCase;
import application.casosdeuso.estado.ListarEstadoUseCase;
import domain.entidades.Acta;
import domain.entidades.Estado;
import infrastructure.persistencia.repositorioimpl.ActaRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EstadoRepositoryImpl;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class FrmBandejaActa extends javax.swing.JPanel {

    public FrmBandejaActa() {
        initComponents();
        cargarEstadoEnComboBox();
        cargarTabla();
    }

    private Estado crearPlaceholderEstado() {
        // ID 0 o -1 como valor inválido para validar después
        return new Estado(0,"-- Seleccione un Área --","");
    }
    
    private void cargarEstadoEnComboBox() {
        ListarEstadoUseCase EstadoUseCase = new ListarEstadoUseCase(new EstadoRepositoryImpl());
        List<Estado> estados = EstadoUseCase.listar();

        DefaultComboBoxModel<Estado> model = new DefaultComboBoxModel<>();
        model.addElement(crearPlaceholderEstado());

        for (Estado estado : estados) {
            model.addElement(estado);
        }

        cb_Estado.setModel(model);
    }
    
    private void cargarTabla() {
        ListarActaUseCase acta = new ListarActaUseCase(new ActaRepositoryImpl());
        List<Acta> lista = acta.Listar();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10; // Solo la columna de botones
            }
        };

        modelo.setColumnIdentifiers(new Object[]{
            "ID", "Fecha", "Tipo", "Empleado", "Área", "Registrador",
            "Soporte", "Aprobador", "Comentario", "Estado", "Acciones"
        });

        for (Acta a : lista) {

            // 👉 Panel con 2 botones ✔ ❌ lado a lado
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));

            // ✔ Botón verde
            JButton btnOk = new JButton("A");
            btnOk.putClientProperty("JButton.buttonType", "roundRect");
            btnOk.setPreferredSize(new Dimension(40, 28));
            btnOk.setBackground(new Color(0, 180, 0));   // Verde
            btnOk.setForeground(Color.WHITE);

            // ❌ Botón rojo
            JButton btnNo = new JButton("R");
            btnNo.putClientProperty("JButton.buttonType", "roundRect");
            btnNo.setPreferredSize(new Dimension(40, 28));
            btnNo.setBackground(new Color(200, 0, 0));   // Rojo
            btnNo.setForeground(Color.WHITE);

            acciones.add(btnOk);
            acciones.add(btnNo);

            modelo.addRow(new Object[]{
                a.getIdActa(),
                a.getFecha(),
                a.getTipoActa(),
                a.getEmpleadoNombres(),
                a.getEmpleadoArea(),
                a.getRegistradorUsuario(),
                a.getFechaSoporte(),
                a.getAprobadorUsuario(),
                a.getComentario(),
                a.getEstadoNombre(),
                acciones
            });
        }

        tb_Acta.setModel(modelo);

        // 👉 Renderer para que se muestren los botones en la tabla
        tb_Acta.getColumnModel().getColumn(10).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            return (Component) value;
        });
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Acta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_Empleado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cb_Estado = new javax.swing.JComboBox<>();

        tb_Acta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IdActa", "Fecha", "Tipo de Acta", "Empleado", "Soporte", "Fecha de Soporte", "Coordinador", "Fecha de Coordinador", "Comentario", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tb_Acta);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Bandeja de Actas");

        jLabel2.setText("Nombre de Empleado:");

        jLabel3.setText("Estado: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 647, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Estado> cb_Estado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Acta;
    private javax.swing.JTextField txt_Empleado;
    // End of variables declaration//GEN-END:variables
}
