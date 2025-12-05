/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Acta;
import domain.entidades.DetalleActa;
import domain.entidades.Equipo;
import domain.entidades.ReporteTrazabilidad;
import domain.repositorio.IActaRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Scott.perez
 */
public class ActaRepositoryImpl implements IActaRepository {

    @Override
    public boolean Registrar(Acta acta) {
        Connection con = null;
        CallableStatement csActa = null;
        CallableStatement csDetalle = null;
        boolean exito = false;
        int idActaGenerado = 0; 

        // Stored Procedures (Asegúrate de que existan en tu DB con esta firma)
        final String SP_REGISTRAR_ACTA = "{CALL sp_registrar_acta(?,?,?,?,?)}";
        final String SP_REGISTRAR_DETALLE_ACTA = "{CALL sp_registrar_detalle_acta(?,?,?,?,?,?)}";

        try {
            
            con = MySQLConnection.obtenerConexion();
            con.setAutoCommit(false);

           
            csActa = con.prepareCall(SP_REGISTRAR_ACTA);
            
            
            csActa.setDate(1, new java.sql.Date(acta.getFecha().getTime()));
            csActa.setString(2, acta.getTipoActa());
            csActa.setInt(3, acta.getIdEmpleado());
            csActa.setInt(4, acta.getIdUsuarioSoporte());
            csActa.setString(5, acta.getComentario());
            
            
            try (ResultSet rs = csActa.executeQuery()) {
                if (rs.next()) {
                    idActaGenerado = rs.getInt("IdActa");
                } else {
                    
                    throw new SQLException("El SP 'sp_registrar_acta' no retornó el ID del Acta.");
                }
            }

            
            if (idActaGenerado > 0) {
                csDetalle = con.prepareCall(SP_REGISTRAR_DETALLE_ACTA);
                for (DetalleActa d : acta.getDetalles()) {
                    csDetalle.setInt(1, idActaGenerado); 
                    csDetalle.setInt(2, d.getIdEquipo());
                    csDetalle.setInt(3, acta.getIdEmpleado());
                    csDetalle.setInt(4, acta.getIdUsuarioSoporte());
                    csDetalle.setString(5, acta.getTipoActa());
                    csDetalle.setString(6, acta.getComentario());
                    csDetalle.addBatch(); 
                }
                csDetalle.executeBatch(); 
            } else {
                throw new SQLException("Fallo al obtener el ID del Acta. Detalles no registrados.");
            }

          
            con.commit();
            System.out.println("✅ Acta registrada correctamente con sus detalles. ID generado: " + idActaGenerado);
            exito =true;

        } catch (SQLException e) {
            // 4️⃣ Manejo de errores y Rollback
            try {
                if (con != null) con.rollback(); // Revertir si algo falló
            } catch (SQLException ex) {
                System.err.println("Error durante el rollback: " + ex.getMessage());
                ex.printStackTrace();
            }
            System.err.println("❌ Error al registrar acta (SP/Batch): " + e.getMessage());
            e.printStackTrace();
            return false; // Indicar fallo
            
        } finally {
            // 5️⃣ Cerrar recursos
            try {
                if (csActa != null) csActa.close();
                if (csDetalle != null) csDetalle.close();
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar el modo auto-commit
                    con.close();
                }
            } catch (SQLException e) { 
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return exito;
    }

    @Override
   public Acta buscarPorId(int idActa) {
        String sql = "{CALL sp_ObtenerActaDetalle(?)}";
        Acta acta = null;
        List<DetalleActa> detalles = new ArrayList<>();

        try (Connection conn = MySQLConnection.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idActa);
            boolean hadResults = cs.execute();

            // 1. Procesar el Primer Resultado: CABECERA DEL ACTA
            if (hadResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                   

                        acta = new Acta(
                            rs.getString("FechaRegistro"),
                            rs.getString("TipoActa"),
                            rs.getString("Comentario"),
                            rs.getString("EmpleadoNombres"),
                            rs.getString("EmpleadoArea"),
                            rs.getString("RegistradorUsuario"),
                            rs.getString("FechaSoporte"),
                            rs.getString("AprobadorUsuario"),
                            rs.getString("FechaCoordinador"),
                            new ArrayList<>() ,// Inicializar la lista de detalles vacía
                           rs.getInt("IdEstado")
                        );
                    }
                }
            }

            // 2. Procesar el Segundo Resultado: DETALLES DEL ACTA
            if (acta != null && cs.getMoreResults()) {
                try (ResultSet rsDetalle = cs.getResultSet()) {
                    while (rsDetalle.next()) {
                        // Mapear el Equipo
                        Equipo equipo = new Equipo(
                            rsDetalle.getInt("IdEquipo"),
                            rsDetalle.getString("EquipoNombre"), // o serie
                            rsDetalle.getString("EquipoMarca"),
                            rsDetalle.getString("EquipoModelo"),
                            0 // Estado (no esencial para el Detalle)
                        );
                        
                        // Crear DetalleActa (la entidad de dominio)
                        DetalleActa detalle = new DetalleActa();
                        detalle.setIdDetalleActa(rsDetalle.getInt("IdDetalleActa"));
                        detalle.setEquipo(equipo); 
                      
                        
                        detalles.add(detalle);
                    }
                }
                // Asignar los detalles al acta
                acta.setDetalles(detalles);
            }

            return acta;

        } catch (SQLException e) {
            System.err.println("Error al buscar acta por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean aprobar(int idActa, int idCoordinador) {
       // Asumiendo que usas un SP llamado sp_aprobar_acta
    final String SP_APROBAR_ACTA = "{CALL sp_Aprobar_acta(?, ?)}"; 
    
    try (Connection con = MySQLConnection.obtenerConexion();
         CallableStatement cs = con.prepareCall(SP_APROBAR_ACTA)) {
        
        cs.setInt(1, idActa);
        cs.setInt(2, idCoordinador);

        int filasAfectadas = cs.executeUpdate();
        
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("❌ Error al aprobar acta (SP): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    }

    @Override
    public boolean rechazar(int idActa, int idCoordinador) {
    
    final String SP_RECHAZAR_ACTA = "{CALL sp_rechazar_acta(?, ?)}"; 
    
    try (Connection con = MySQLConnection.obtenerConexion();
         CallableStatement cs = con.prepareCall(SP_RECHAZAR_ACTA)) {
        
       
        cs.setInt(1, idActa);
        cs.setInt(2, idCoordinador);
   

        int filasAfectadas = cs.executeUpdate();
        
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("❌ Error al rechazar acta (SP): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    }

    @Override
    public List<Acta> Listar(String Nombres) {
        List<Acta> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarActas(?)}";  // 🔹 CORREGIDO
       // 1. Declarar solo recursos AutoCloseable en try-with-resources
    try (Connection con = MySQLConnection.obtenerConexion();
         CallableStatement cs = con.prepareCall(sql)) {

        // 2. Asignar el parámetro e.g., cs.setInt(...)
        cs.setString(1, Nombres);

        // 3. Ejecutar la consulta y obtener el ResultSet
        try (ResultSet rs = cs.executeQuery()) { 

            while (rs.next()) {
                Acta a = new Acta();
            a.setIdActa(rs.getInt("IdActa"));
            a.setFecha(rs.getDate("Fecha"));
            a.setTipoActa(rs.getString("TipoActa"));
            a.setEmpleadoNombres(rs.getString("EmpleadoNombres"));
            a.setEmpleadoArea(rs.getString("EmpleadoArea"));
            a.setRegistradorUsuario(rs.getString("RegistradorUsuario"));
            a.setFechaSoporte(rs.getString("FechaSoporte"));
            a.setAprobadorUsuario(rs.getString("AprobadorUsuario"));
            a.setFechaCoordinador(rs.getString("FechaCoordinador"));
            a.setComentario(rs.getString("Comentario"));
            a.setEstadoNombre(rs.getString("Estado"));

                lista.add(a);
          }
        } // El ResultSet se cierra automáticamente aquí

    } catch (Exception e) {
        System.err.println("❌ Error al listar equipos: " + e.getMessage());
    }
    return lista;
    }

    @Override
    public List<ReporteTrazabilidad> listarTrazabilidad(int idEquipo) {
       
        List<ReporteTrazabilidad> lista = new ArrayList<>();

    String sql = "{CALL sp_reporte_trazabilidad_equipo(?)}";

    try (Connection conn = MySQLConnection.obtenerConexion();
         CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setInt(1, idEquipo);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ReporteTrazabilidad r = new ReporteTrazabilidad();
               // r.setIdHistorial(rs.getInt("IdHistorial"));
                r.setIdEquipo(rs.getInt("IdEquipo"));
                r.setNombreEquipo(rs.getString("NombreEquipo"));
                r.setMarca(rs.getString("Marca"));
                r.setModelo(rs.getString("Modelo"));
                r.setIdEmpleado(rs.getInt("IdEmpleado"));
                r.setEmpleado(rs.getString("Empleado"));
                r.setArea(rs.getString("Area"));
                r.setIdActa(rs.getInt("IdActa"));
                r.setTipoActa(rs.getString("TipoActa"));
                r.setFecha(rs.getDate("Fecha"));
                r.setTipoMovimiento(rs.getString("TipoMovimiento"));
               // r.setFechaMovimiento(rs.getTimestamp("FechaMovimiento"));
               // r.setObservacion(rs.getString("Observacion"));

                lista.add(r);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
    }
    
}
