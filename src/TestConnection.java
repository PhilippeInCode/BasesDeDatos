import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/pruebabase";
		String user = "root";
		String password = "";
		try {
			Connection cnx = DriverManager.getConnection(url, user, password);
			System.out.println("Estado de la conexión");
			System.out.println(cnx.isClosed() ? "cerrada" : "abierta");
		} catch (SQLException e) {
			System.out.println("Un error se ha producido durante la conexión a la base de datos");
			e.printStackTrace();
		}
	}
}
