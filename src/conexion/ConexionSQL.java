package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {
	private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://locahost:1433; database=bd_ventas; user=sa; password=123456; loginTimeout=30;";
	
	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver no encontrado");
			ex.printStackTrace();
		}
	}
	
	public Connection Conectar() {
		Connection cnx = null;
		
		try {
			cnx = DriverManager.getConnection(URL);
			System.out.println("Conectado...");
		} catch (SQLException ex) {
			System.out.println("No se conectó a la BD");
			ex.printStackTrace();
		}
		
		return cnx;
	}
}
