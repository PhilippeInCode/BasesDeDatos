import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MasResultados {
	public static void main(String[] args) {
		boolean resultado;
		ResultSet rs;
		try (Connection cnx = getConnection();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Rellena sus instrucciones SQL separadas por ; ;");
			String consulta = br.readLine();
			resultado = stm.execute(consulta);
			int i = 1;

			if (resultado) {
				System.out.println("Su instrucción Nº " + i + " ha generado un juego de registros");
				rs = stm.getResultSet();
				rs.last();
				System.out.println("Contiene " + rs.getRow() + " registros");
			} else {
				System.out.println("Su instrucción Nº " + i + " ha modificado los registros en la base");
				System.out.println("Número de registros modificados: " + stm.getUpdateCount());
			}
			i++;

			resultado = stm.getMoreResults();

			while (resultado || stm.getUpdateCount() != -1) {
				if (resultado) {
					System.out.println("Su instrucción Nº " + i + " ha generado un juego de registros");
					rs = stm.getResultSet();
					rs.last();
					System.out.println("Contiene " + rs.getRow() + " registros");
				} else {
					System.out.println("Su instrucción Nº " + i + " ha modificado los registros en la base");
					System.out.println("Número de registros modificados: " + stm.getUpdateCount());
				}
				i++;

				resultado = stm.getMoreResults();
			}
		} catch (SQLException e) {
			System.out.println("Su instrucción no ha funcionado correctamente");
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
