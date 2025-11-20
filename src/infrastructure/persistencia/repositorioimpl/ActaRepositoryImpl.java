/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Acta;
import domain.entidades.DetalleActa;
import domain.entidades.Equipo;
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
        int idActaGenerado = 0; // Usamos un entero para retornar el ID

        // Stored Procedures (Asegúrate de que existan en tu DB con esta firma)
        final String SP_REGISTRAR_ACTA = "{CALL sp_registrar_acta(?,?,?,?,?)}";
        final String SP_REGISTRAR_DETALLE_ACTA = "{CALL sp_registrar_detalle_acta(?,?)}";

        try {
            // Obtener Conexión (Asumo que esta clase es el antiguo MySQLConnection)
            con = MySQLConnection.obtenerConexion();
            con.setAutoCommit(false); // 🔒 Inicia transacción

            // 1️⃣ Registrar el encabezado del acta
            csActa = con.prepareCall(SP_REGISTRAR_ACTA);
            
            // Conversión de java.util.Date a java.sql.Date
            csActa.setDate(1, new java.sql.Date(acta.getFecha().getTime()));
            csActa.setString(2, acta.getTipoActa());
            csActa.setInt(3, acta.getIdEmpleado());
            csActa.setInt(4, acta.getIdUsuarioSoporte());
            csActa.setString(5, acta.getComentario());
            
            // Ejecutar el SP y obtener el ResultSet que contiene el ID generado
            try (ResultSet rs = csActa.executeQuery()) {
                if (rs.next()) {
                    idActaGenerado = rs.getInt("IdActa");
                } else {
                    // Si el SP no devuelve el ID, es un error crítico
                    throw new SQLException("El SP 'sp_registrar_acta' no retornó el ID del Acta.");
                }
            }

            // 2️⃣ Registrar cada detalle usando Batch
            if (idActaGenerado > 0) {
                csDetalle = con.prepareCall(SP_REGISTRAR_DETALLE_ACTA);
                for (DetalleActa d : acta.getDetalles()) {
                    csDetalle.setInt(1, idActaGenerado); // Usar el ID recién generado
                    csDetalle.setInt(2, d.getIdEquipo());
                
                    csDetalle.addBatch(); // Añadir la instrucción al lote
                }
                csDetalle.executeBatch(); // Ejecutar todas las inserciones en lote
            } else {
                throw new SQLException("Fallo al obtener el ID del Acta. Detalles no registrados.");
            }

            // 3️⃣ Finalizar la transacción
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
                            rs.getString("Acta"),
                            rs.getString("RegistradorUsuario"),
                            rs.getString("FechaSoporte"),
                            rs.getString("AprobadorUsuario"),
                            rs.getString("FechaCoordinador"),
                            new ArrayList<>() // Inicializar la lista de detalles vacía
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
        
        // Parámetros: ID del Acta y ID del Coordinador
        cs.setInt(1, idActa);
        cs.setInt(2, idCoordinador);

        // Se espera que el SP retorne el número de filas afectadas
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
        // Asumiendo que usas un SP llamado sp_rechazar_acta
    final String SP_RECHAZAR_ACTA = "{CALL sp_rechazar_acta(?, ?)}"; 
    
    try (Connection con = MySQLConnection.obtenerConexion();
         CallableStatement cs = con.prepareCall(SP_RECHAZAR_ACTA)) {
        
        // Parámetros: ID del Acta, ID del Coordinador, y el Comentario con el Motivo
        cs.setInt(1, idActa);
        cs.setInt(2, idCoordinador);
        //cs.setString(3, comentario); // Actualiza el comentario en la tabla Acta

        int filasAfectadas = cs.executeUpdate();
        
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("❌ Error al rechazar acta (SP): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    }

    @Override
    public List<Acta> Listar() {
        List<Acta> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarActas()}";  // 🔹 CORREGIDO

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

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

        } catch (Exception e) {
            System.err.println("❌ Error al listar áreas: " + e.getMessage());
        }
        return lista;
    }
    
}
