import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    static Connection conn = null;

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        String enter;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tecnologicomm?" + "user=alex&password=alex");

            // Do something with the Connection
            System.out.println("Bienvenido al sistema");
            System.out.println("Presiona ENTER");
            enter = teclado.nextLine();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
