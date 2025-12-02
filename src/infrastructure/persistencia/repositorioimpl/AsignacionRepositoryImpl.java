/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Area;
import domain.entidades.AsignacionReporte;
import domain.entidades.Equipo;
import domain.repositorio.IAsignacionRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Scott.perez
 */
public class AsignacionRepositoryImpl implements  IAsignacionRepository {

    @Override
    public List<AsignacionReporte> listarAsignacionesActivas(int IdEmpleado) {
        List<AsignacionReporte> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarAsignaciones_Filtros(?)}";  // 🔹 CORREGIDO
    
        // 1. Declarar solo recursos AutoCloseable en try-with-resources
    try (Connection con = MySQLConnection.obtenerConexion();
         CallableStatement cs = con.prepareCall(sql)) {

        // 2. Asignar el parámetro e.g., cs.setInt(...)
        cs.setInt(1, IdEmpleado);

        // 3. Ejecutar la consulta y obtener el ResultSet
        try (ResultSet rs = cs.executeQuery()) { 
            while (rs.next()) {
               lista.add(new AsignacionReporte(
                        rs.getInt("IdAsignacion"),
                        rs.getString("FechaEntrega"),
                        rs.getString("NombreEquipo"),
                        rs.getString("Modelo"),
                        rs.getString("EmpleadoAsignado"),
                        rs.getString("NombreArea")
                ));
            }
        } // El ResultSet se cierra automáticamente aquí

    } catch (Exception e) {
        System.err.println("❌ Error al listar equipos: " + e.getMessage());
    }
    return lista;
    }
       
}
