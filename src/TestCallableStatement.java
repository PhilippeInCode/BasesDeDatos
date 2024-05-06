import java.io.*;
import java.sql.*;

public class TestCallableStatement {
    public static void main(String[] args) {
        try (Connection cnx = getConnection();
             CallableStatement cstm = cnx.prepareCall("{call nombreProcedimiento (?, ?, ?, ?)}")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Introduce el valor para el primer parámetro:");
            String parametro1 = br.readLine();

            System.out.println("Introduce el valor para el segundo parámetro:");
            String parametro2 = br.readLine();

            System.out.println("Introduce el valor para el tercer parámetro:");
            String parametro3 = br.readLine();

            System.out.println("Introduce el valor para el cuarto parámetro:");
            String parametro4 = br.readLine();

            // Aplicación de los parámetros
            cstm.setString(1, parametro1);
            cstm.setString(2, parametro2);
            cstm.setString(3, parametro3);
            cstm.setString(4, parametro4);

            // Ejecución de la consulta
            cstm.execute();
            System.out.println("Procedimiento ejecutado correctamente.");
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