package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Estado;
import domain.repositorio.IEstadoRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoRepositoryImpl implements IEstadoRepository{

    @Override
    public List<Estado> Listar() {
        List<Estado> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarEstados()}";  // 🔹 CORREGIDO

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Estado estado = new Estado();
                estado.setIdEstado(rs.getInt("IdEstado"));   // 🔹 CORRECTO
                estado.setNombre(rs.getString("Nombre"));  
                estado.setDescripcion(rs.getString("Descripcion"));

                lista.add(estado);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al listar áreas: " + e.getMessage());
        }
        return lista;
    }
    
}
