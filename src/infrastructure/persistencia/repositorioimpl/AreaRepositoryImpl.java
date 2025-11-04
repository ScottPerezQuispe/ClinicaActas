/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Area;
import domain.entidades.Equipo;
import domain.repositorio.IAreaRepository;
import domain.repositorio.IEquipoRepository;
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
public class AreaRepositoryImpl implements IAreaRepository {

    @Override
    public List<Area> Listar() {
        List<Area> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarEquipos()}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Area eq = new Area();
                eq.setIdArea(rs.getInt("IdEquipo"));
                eq.setNombre(rs.getString("Nombre"));
              
               
                lista.add(eq);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }
    
}
