/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Equipo;
import domain.repositorio.IEquipoRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.*;
/**
 *
 * @author Scott.perez
 */
public class EquipoRepositoryImpl implements IEquipoRepository {

    @Override
    public boolean Insertar(Equipo equipo) {
        boolean exito = false;
        String sql = "{CALL sp_InsertarEquipo(?,?,?,?)}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, equipo.getNombreEquipo());
            cs.setString(2, equipo.getMarca());
            cs.setString(3, equipo.getModelo());  
            cs.setInt(4, equipo.getCantidad());

            exito = cs.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error al insertar equipo: " + e.getMessage());
        }
        return exito;
    }
    
}
