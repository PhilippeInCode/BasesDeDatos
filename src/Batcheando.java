import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Batcheando {
	public static void main(String[] args) {
		try (Connection cnx = getConnection();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Rellenar sus intrucciones SQL y ejecutarlas en lote: ");
			String consulta = br.readLine();
			while (!consulta.equalsIgnoreCase("run")) {
				stm.addBatch(consulta);
				consulta = br.readLine();
			}
			System.out.println("Ejecución del lote de instrucciones");
			int[] resultados = stm.executeBatch();
			for (int i = 0; i < resultados.length; i++) {
				switch (resultados[i]) {
				case Statement.EXECUTE_FAILED:
					System.out.println("La ejecución de la instrucción " + i + " ha fallado");
					break;
				case Statement.SUCCESS_NO_INFO:
					System.out.println("La ejecución de la instrucción " + i + " ha tenido éxito");
					System.out.println("El número de registros modificados es desconocido");
					break;
				default:
					System.out.println("La ejecución de la instrucción " + i + " ha tenido éxito");
					System.out.println("Ha modificado " + resultados[i] + " registros");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("Su batch no ha funcionado correctamente " + e.getMessage());
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
