/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.repositorioimpl;
import domain.entidades.Usuario;
import domain.repositorio.IUsuarioRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.*;
/**
 *
 * @author Scott.perez
 */
public class UsuarioRepositoryImpl implements IUsuarioRepository {

    public Usuario validarLogin(String usuario, String clave) {
        Usuario user = null;
        String sql = "{CALL sp_validar_login(?, ?)}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, usuario.trim());
            cs.setString(2, clave.trim());

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    user = new Usuario(
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdPersonal"),
                        rs.getInt("IdRol"),
                        rs.getString("Usuario"),
                        rs.getString("Clave"),
                            rs.getInt("IdEstado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al validar login: " + e.getMessage());
        }
        return user;
    }
}