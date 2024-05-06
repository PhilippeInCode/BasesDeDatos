import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestPreparedStatement {

	public static void main(String[] args) {
		try (Connection cnx = getConnection();
				PreparedStatement pstm = cnx.prepareStatement(
						"INSERT INTO equipo (id, nombreEquipo, cantJugadores) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Ingrese el valor para el id:");
			String id = br.readLine();

			System.out.println("Ingrese el valor para el nombre del equipo:");
			String nombreEquipo = br.readLine();

			System.out.println("Ingrese el valor para la cantidad de jugadores:");
			String cantJugadores = br.readLine();

			// Aplicar los parámetros
			pstm.setString(1, id);
			pstm.setString(2, nombreEquipo);
			pstm.setString(3, cantJugadores);

			// Ejecuta la consulta y obtiene el resultado
			pstm.executeUpdate();
			// Recuperar el ResultSet que eventualmente contiene una clave
			ResultSet rsClaveGenerada = pstm.getGeneratedKeys();
			// Si hay un registro, es el que tiene una clave
			if (rsClaveGenerada.next()) {
				System.out.println("La clave del registro generado es: " + rsClaveGenerada.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
