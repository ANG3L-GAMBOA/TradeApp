package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	
	private static final String URL = "jdbc:mysql://localhost:3306/bd_ventas";
	
	public Connection Conectar() {
		Connection cnx = null;
		
		try {
			cnx = DriverManager.getConnection(URL, "root", "");
			System.out.println("Conectado...");
		} catch (SQLException ex) {
			System.out.println("No se conectó a la BD");
			ex.printStackTrace();
		}
		
		return cnx;
	}
}// class ends
