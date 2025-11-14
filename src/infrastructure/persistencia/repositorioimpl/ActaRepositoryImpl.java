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

        try {
            con = MySQLConnection.obtenerConexion();
            con.setAutoCommit(false); // 🔒 Inicia transacción

            // 1️⃣ Registrar el encabezado del acta
            csActa = con.prepareCall("{CALL sp_registrar_acta(?,?,?,?,?)}");
            csActa.setDate(1, new java.sql.Date(acta.getFecha().getTime()));
            csActa.setInt(2, acta.getIdTipo());
            csActa.setInt(3, acta.getIdEmpleado());
            csActa.setInt(4, acta.getIdUsuarioSoporte());
            csActa.setString(5, acta.getObservacion());
            ResultSet rs = csActa.executeQuery();

            int idActa = 0;
            if (rs.next()) {
                idActa = rs.getInt("IdActa");
            }

            // 2️⃣ Registrar cada detalle
            csDetalle = con.prepareCall("{CALL sp_registrar_detalle_acta(?,?)}");
            for (DetalleActa d : acta.getDetalles()) {
                csDetalle.setInt(1, idActa);
                csDetalle.setInt(2, d.getIdEquipo());
         
                csDetalle.addBatch();
            }
            csDetalle.executeBatch();

            con.commit();
            exito = true;
            System.out.println("✅ Acta registrada correctamente con sus detalles.");

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("❌ Error al registrar acta: " + e.getMessage());
        } finally {
            try {
                if (csActa != null) csActa.close();
                if (csDetalle != null) csDetalle.close();
                if (con != null) con.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return exito;
    }
    
}
