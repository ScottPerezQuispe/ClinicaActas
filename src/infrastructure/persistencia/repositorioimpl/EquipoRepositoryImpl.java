/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Equipo;
import domain.repositorio.IEquipoRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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
    
    @Override
    public boolean Actualizar(Equipo equipo) {
        boolean exito = false;
        String sql = "{CALL sp_ActualizarEquipo(?,?,?,?,?)}"; // Nuevo procedimiento

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, equipo.getIdEquipo());
            cs.setString(2, equipo.getNombreEquipo());
            cs.setString(3, equipo.getMarca());
            cs.setString(4, equipo.getModelo());
            cs.setInt(5, equipo.getCantidad());

            exito = cs.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error al actualizar equipo: " + e.getMessage());
        }
        return exito;
    }    

    @Override
    public boolean Eliminar(int idEquipo) {
        boolean exito = false;
        String sql = "{CALL sp_EliminarEquipo(?)}";
        
        try (Connection con = MySQLConnection.obtenerConexion()){
            CallableStatement cs = con.prepareCall(sql);
            
            cs.setInt(1, idEquipo);
            exito = cs.executeUpdate() > 0;
            
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar equipo: " + e.getMessage());
        }
        return exito;
    }

    /**
    * Obtiene la lista completa de equipos desde la base de datos.
    * @return una lista de objetos Equipo
    */
    
    @Override
    public List<Equipo> Listar() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarEquipos()}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Equipo eq = new Equipo();
                eq.setIdEquipo(rs.getInt("IdEquipo"));
                eq.setNombreEquipo(rs.getString("Nombre"));
                eq.setMarca(rs.getString("Marca"));
                eq.setModelo(rs.getString("Modelo"));
                eq.setCantidad(rs.getInt("Cantidad"));
               
                lista.add(eq);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Equipo> ListarCombo() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarEquipos()}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Equipo eq = new Equipo();
                eq.setIdEquipo(rs.getInt("IdEquipo"));
                eq.setNombreEquipo(rs.getString("Nombre"));
                eq.setMarca(rs.getString("Marca"));
                eq.setModelo(rs.getString("Modelo"));
                eq.setCantidad(rs.getInt("Cantidad"));
               
                lista.add(eq);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }
    
}
