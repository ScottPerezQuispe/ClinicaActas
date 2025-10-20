/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infrastructure.persistencia.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Scott.perez
 */
public class MySQLConnection {
    
    private static final String URL = "jdbc:mysql://oj7zuz.h.filess.io:61031/BDClinica_learndughe?useSSL=false&serverTimezone=UTC";
    private static final String USER = "BDClinica_learndughe";
    private static final String PASSWORD = "d185862334273ed641447daaa918eea1636c7162";
    private static Connection conexion = null;
    // Constructor privado: patrón Singleton
    private MySQLConnection() {}
    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con la base de datos MySQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: no se encontró el driver MySQL JDBC -> " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos -> " + e.getMessage());
        }
        return conexion;
    }   
     public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
