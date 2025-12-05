/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentation.view;

import application.casosdeuso.acta.GenerarReporteTrazabilidadUseCase;
import application.casosdeuso.acta.ListarActaUseCase;
import application.casosdeuso.acta.ListarAsignacionesActivasUseCase;
import application.casosdeuso.empleado.ListarEmpleadoUseCase;
import application.casosdeuso.equipo.ListarEquipoUseCase;
import domain.entidades.Acta;
import domain.entidades.AsignacionReporte;
import domain.entidades.Empleado;
import domain.entidades.Equipo;
import domain.entidades.ReporteTrazabilidad;
import domain.entidades.Usuario;
import infrastructure.persistencia.repositorioimpl.ActaRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.AsignacionRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EmpleadoRepositoryImpl;
import infrastructure.persistencia.repositorioimpl.EquipoRepositoryImpl;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Scott.perez
 */
public class FrmReporteAsignacion extends javax.swing.JPanel {

    /**
     * Creates new form FrmReporteAsignacion
     */
    private Usuario usuarioLogueado;
    public FrmReporteAsignacion(Usuario usuario) {
        this.usuarioLogueado = usuario;
        initComponents();
        cargarEmpleadosEnComboBox();
        cargarTabla();
         cb_Equipo.addActionListener(e -> {
            cargarTabla();
        });

    }

    private void cargarTabla() {
        GenerarReporteTrazabilidadUseCase  acta = new GenerarReporteTrazabilidadUseCase (new ActaRepositoryImpl());
        
        // 1. Obtener el Equipo seleccionado (que puede ser el placeholder)
        Equipo equipoSeleccionado = (Equipo) cb_Equipo.getSelectedItem();

        // 2. Definir el ID a usar para la consulta
        int idEquipoFiltro;
        if (equipoSeleccionado != null && equipoSeleccionado.getIdEquipo() != 0) {
            // Usar el ID real si se seleccionó un equipo válido
            idEquipoFiltro = equipoSeleccionado.getIdEquipo();
        } else {
            // Usar un ID que represente "listar todos" (asumiendo que 0 o -1 lo hace)
            // O mejor, modificar el UseCase para aceptar null/0 y listar todos.
            idEquipoFiltro = 0; // Asumiendo que 0 significa "listar todos"
        }

        // 3. Ejecutar la consulta con el ID de filtro
        // ASUMIMOS que el UseCase GenerarReporteTrazabilidadUseCase.ejecutar(0)
        // devolverá TODAS las trazabilidades si se pasa 0.
        List<ReporteTrazabilidad> lista = acta.ejecutar(idEquipoFiltro);

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo la columna de botones
            }
        };

        modelo.setColumnIdentifiers(new Object[]{
            "Equipo", "Empleado", "Área", "Tipo de Acta", "Fecha"
        });

        for (ReporteTrazabilidad a : lista) {

            modelo.addRow(new Object[]{
                a.getNombreEquipo(),
                a.getEmpleado(),
                 a.getArea(),
                a.getTipoActa(),
                a.getFecha(),
               
                
               
            });
            
           
            
        }

        tb_Acta.setModel(modelo);


        
  
    }
   
      private Equipo crearPlaceholderEmpleado() {
        // Usamos el constructor y damos valores que no representan un empleado real.
        // El ID se establece a 0 o -1 para poder validarlo después.
        // Los 'nombres' y 'apellidos' se establecen para que toString() funcione.
        return new Equipo(
                0,
                "-- Seleccione un Equipo --",
                "-- Seleccione un Empleado --",
                "",
                0
        );
    }
      private void cargarEmpleadosEnComboBox() {
        ListarEquipoUseCase EquipodoUseCase = new ListarEquipoUseCase(new EquipoRepositoryImpl());
        List<Equipo> equipos = EquipodoUseCase.Listar("");

        // El modelo ahora es de tipo Empleado
        //DefaultComboBoxModel<Empleado> model = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<Equipo> model = new DefaultComboBoxModel<>();
        // Opcional: Agregar un Empleado "dummy" para la selección inicial (con ID -1, por ejemplo)

        // 🎯 Paso CLAVE: Añadir el marcador de posición primero
       model.addElement(crearPlaceholderEmpleado());
        for (Equipo equipo : equipos) {
            model.addElement(equipo); // Agregamos el objeto Empleado directamente
        }

        cb_Equipo.setModel(model);
        // Para que muestre el nombre, la clase Empleado DEBE SOBREESCRIBIR el método toString()
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Acta = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cb_Equipo = new javax.swing.JComboBox<>();

        tb_Acta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NombreEquipo", "Empleado", "Area", "Tipo de Acta", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(tb_Acta);

        jLabel3.setText("Equipo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cb_Equipo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cb_Equipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Equipo> cb_Equipo;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Acta;
    // End of variables declaration//GEN-END:variables
}
