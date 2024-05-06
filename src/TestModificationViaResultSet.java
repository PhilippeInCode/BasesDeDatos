import java.sql.*;

public class TestModificationViaResultSet {
    public static void main(String[] args) {
        try (Connection cnx = getConnection();
             Statement stmModif = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             Statement stmReading = cnx.createStatement()) {
            leerYModificarPersonas(stmModif);
            leerPersonas(stmReading);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void leerYModificarPersonas(Statement stmModif) throws SQLException {
        ResultSet rs = stmModif.executeQuery("SELECT * FROM persona");
        while (rs.next()) {
            // Modificación del nombre y apellidos poniéndolos en mayúsculas
            rs.updateString("nombre", rs.getString("nombre").toUpperCase());
            rs.updateString("apellidos", rs.getString("apellidos").toUpperCase());
            rs.updateRow();
        }
    }

    private static void leerPersonas(Statement stm) throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM persona");
        System.out.println("Nombre\t\tApellidos\t\tEdad\t\tCiudad");
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            int edad = rs.getInt("edad");
            String ciudad = rs.getString("ciudad");

            System.out.println(nombre + "\t\t" + apellidos + "\t\t" + edad + "\t\t" + ciudad);
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/pruebabase";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}