/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Acta;
import domain.entidades.DetalleActa;
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
    
}
