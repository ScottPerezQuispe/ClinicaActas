package infrastructure.persistencia.repositorioimpl;

import domain.entidades.Area;
import domain.entidades.Empleado;
import domain.repositorio.IEmpleadoRepository;
import infrastructure.persistencia.conexion.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryImpl implements IEmpleadoRepository{

    @Override
    public boolean Insertar(Empleado empleado) {
        boolean exito = false;
        String sql = "{CALL sp_InsertarEmpleado(?,?,?,?)}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, empleado.getDni());
            cs.setString(2, empleado.getNombres());
            cs.setString(3, empleado.getApellidos());  
            cs.setInt(4, empleado.getArea().getIdArea());

            exito = cs.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error al insertar empleado: " + e.getMessage());
        }
        return exito;
    }

    @Override
    public boolean Actualizar(Empleado empleado) {
        boolean exito = false;
        String sql = "{CALL sp_ActualizarEmpleado(?,?,?,?,?)}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, empleado.getIdEmpleado());
            cs.setString(2, empleado.getDni());
            cs.setString(3, empleado.getNombres());
            cs.setString(4, empleado.getApellidos());
             cs.setInt(4, empleado.getArea().getIdArea());

            exito = cs.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error al actualizar empleado: " + e.getMessage());
        }
        return exito;
    }

    @Override
    public boolean Eliminar(int idEmpleado) {
        boolean exito = false;
        String sql = "{CALL sp_EliminarEmpleado(?)}";
        
        try (Connection con = MySQLConnection.obtenerConexion()){
            CallableStatement cs = con.prepareCall(sql);
            
            cs.setInt(1, idEmpleado);
            exito = cs.executeUpdate() > 0;
            
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar Empleado: " + e.getMessage());
        }
        return exito;
    }

    @Override
    public List<Empleado> Listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "{CALL sp_ListarEmpleados()}";

        try (Connection con = MySQLConnection.obtenerConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                // 1. Crear la entidad dependiente: Area
                Area area = new Area();
                // Asumiendo que 'Area' tiene un constructor o setters para idArea y nombre
                area.setIdArea(rs.getInt("idArea")); 
                area.setNombre(rs.getString("nombreArea"));

                // 2. Crear la entidad principal: Empleado
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("idEmpleado"));
                emp.setDni(rs.getString("dni"));         // Usamos 'dni' de la entidad
                emp.setNombres(rs.getString("nombres"));
                emp.setApellidos(rs.getString("apellidos"));

                // 3. Establecer la relación
                emp.setArea(area); // <--- Esto enlaza Empleado con Area

                lista.add(emp);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al listar Empleados: " + e.getMessage());
        }
        return lista;
    }
    
}
