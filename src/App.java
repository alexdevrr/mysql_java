import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {
    static Connection conn = null;
    static ResultSet rs = null;

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        Statement stmt = null;
        String enter;

        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/tecnologicomm?" + "user=alex&password=alex");

            // Do something with the Connection
            System.out.println("Bienvenido al sistema");
            System.out.println("Presiona ENTER");
            enter = teclado.nextLine();
            teclado.close();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM alumnos");
            while (rs.next()) {
                System.out.println(rs.getString("nocontrol"));
                System.out.println(rs.getString("nombre"));
                System.out.println(rs.getInt("calificicacion"));
                System.out.println(rs.getDate("fecha"));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
