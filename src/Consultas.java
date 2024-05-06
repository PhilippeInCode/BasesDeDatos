import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {
	public static void main(String[] args) {
		try (Connection cnx = getConnection();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Introducir su instrucción SQL: ");
			String sentencia = br.readLine();
			boolean resultado = stm.execute(sentencia);

			if (resultado) {
				System.out.println("Su sentencia ha generado un juego de registros");
				ResultSet rs = stm.getResultSet();
				rs.last();
				System.out.println("Contiene " + rs.getRow() + " registros");

			} else {
				System.out.println("Su sentencia ha modificado registros en la base");
				System.out.println("Registros modificados: " + stm.getUpdateCount());
			}
		} catch (SQLException e) {
			System.out.println("Su sentencia no ha funcionado correctamente");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost/pruebabase";
		String user = "root";
		String password = "";
		return DriverManager.getConnection(url, user, password);
	}
}
