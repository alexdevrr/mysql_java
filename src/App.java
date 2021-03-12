
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;
// TODO: OJO! CAMBIAR EL STRING DE "calificicacion" si va a usarse en otra DB que no sea local ya que está mal escrito

public class App {
    static Connection conn = null;
    static ResultSet rs = null;

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        Statement stmt = null;
        // String queryAdd = "insert into alumnos values('666', 'Pedro', 50, now())";

        // String queryUpdate = "UPDATE alumnos SET nombre='Prueba1', calificicacion=50,
        // fecha='2001-05-12' "
        // + "WHERE nocontrol=555";

        int total = 0;
        boolean salir = false;
        int opcion = 0;
        String mensaje = "Presione cualquier número para volver al menú principal";
        String where = "123";
        String queryUpdate = "UPDATE alumnos SET nombre = ?, calificicacion = ?, fecha = now()" + "  "
                + "WHERE nocontrol = ?";

        // String queryUpdate = "UPDATE alumnos SET nombre = ?, calificicacion = ?,
        // fecha = now()" + " "
        // + "WHERE nocontrol = '" + where + "'";

        // String where = "WHERE nocontrol = ?";

        String consulta = "select * from alumnos where nocontrol = ?";

        String agregar = "insert into alumnos values(?, ?, ?, now())";

        // TODO: PENDIENTE
        String queryDelete = "delete from alumnos where nocontrol = ?";
        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/tecnologicomm?" + "user=alex&password=alex");

            // conn es la "instancia" de la conexión
            Statement st = conn.createStatement();

            System.out.println("\n Estás conectado a la Base de datos :D \n");

            do {
                System.out.println("Escribe una de las opciones: \n");

                System.out.println("1.-Ingresar registros");
                System.out.println("2.-Leer registros");
                System.out.println("3.-Actualizar registros");
                System.out.println("4.-Borrar un registro");
                System.out.println("5.-Consultar alumno por su número de control");
                System.out.println("6.-Salir");

                // Evita el bucle
                opcion = teclado.nextInt();
                // Evita que el contador almacene valores
                total = 0;

                switch (opcion) {
                case 1:
                    System.out.println("«Has seleccionado Create»");

                    Scanner scannerAdd1 = new Scanner(System.in);
                    Scanner scannerAdd2 = new Scanner(System.in);
                    Scanner scannerAdd3 = new Scanner(System.in);

                    System.out.println("Ingrese el numero de control para el usuario que desee registrar: ");
                    // nocontrol
                    String nocontrolAdd = scannerAdd1.nextLine();
                    // nombre
                    System.out.println("Ahora ingrese el nombre: ");
                    String nombreAdd = scannerAdd2.nextLine();
                    // calificacion
                    System.out.println("Por último, ingrese su calificación del 1 al 100");
                    String calificacionAdd = scannerAdd3.nextLine();

                    // Preparar la consulta
                    PreparedStatement sentenciaAdd = conn.prepareStatement(agregar);

                    sentenciaAdd.setString(1, nocontrolAdd);
                    sentenciaAdd.setString(2, nombreAdd);
                    sentenciaAdd.setInt(3, Integer.valueOf(calificacionAdd));
                    // HACE EL REGISTRO
                    sentenciaAdd.executeUpdate();

                    System.out.println("\nEl registro se ha hecho exitosamente\n");
                    System.out.println(mensaje);
                    opcion = teclado.nextInt();

                    break;

                case 2:
                    System.out.println("«Has seleccionado Read»");
                    System.out.println(
                            "========================================================================================");
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM alumnos");

                    // Read
                    while (rs.next()) {
                        System.out.println("nocontrol: " + rs.getString("nocontrol") + "     " + "nombre: "
                                + rs.getString("nombre") + "     " + "calificicacion: " + rs.getInt("calificicacion")
                                + "     " + "fecha: " + rs.getDate("fecha"));
                        total++;
                    }

                    System.out.println("El total de registros de la base de datos es de: " + total);
                    System.out.println(
                            "========================================================================================");
                    // Thread.sleep(5 * 1000);
                    System.out.println(mensaje);
                    opcion = teclado.nextInt();
                    break;
                case 3:
                    System.out.println("\n«Has seleccionado la opción Update» \n");

                    Scanner scannerUpdate1 = new Scanner(System.in);
                    Scanner scannerUpdate2 = new Scanner(System.in);
                    Scanner scannerUpdate3 = new Scanner(System.in);

                    System.out.println("¿Cuál es el id del usuario que va a modificar? ");

                    String whereUpdate = scannerUpdate3.next();

                    System.out.println("Ingrese el nuevo nombre para el usuario:  ");

                    String nombreUpdate = scannerUpdate1.nextLine();

                    System.out.println("Ingrese la nueva calificación para el usuario: ");
                    String calificacionUpdate = scannerUpdate2.nextLine();

                    // Preparar la consulta
                    PreparedStatement sentenciaUpdate = conn.prepareStatement(queryUpdate);
                    // PreparedStatement whereUpdate = conn.prepareStatement(where);

                    sentenciaUpdate.setString(1, nombreUpdate);
                    sentenciaUpdate.setInt(2, Integer.valueOf(calificacionUpdate));
                    sentenciaUpdate.setString(3, whereUpdate);

                    sentenciaUpdate.executeUpdate();

                    System.out.println("Actualización hecha correctamente\n");
                    System.out.println(mensaje);
                    opcion = teclado.nextInt();
                    break;
                case 4:
                    System.out.println("«Has seleccionado Delete»");

                    Scanner scannerDelete1 = new Scanner(System.in);

                    System.out.println("Digita el nocontrol del usuario al que desees eliminar: ");

                    String userDelete = scannerDelete1.nextLine();

                    PreparedStatement sentenciaDelete = conn.prepareStatement(queryDelete);

                    sentenciaDelete.setString(1, userDelete);
                    sentenciaDelete.executeUpdate();

                    System.out.println("\nUsuario eliminado correctamente\n");
                case 5:
                    System.out.println("Seleccionaste Read");

                    // Preparar la consulta
                    PreparedStatement sentenciaCon = conn.prepareStatement(consulta);

                    Scanner scannerCon = new Scanner(System.in);

                    System.out.println("Ingrese el numero de control del usuario que desea consultar: ");
                    // Aquí comienza a pedir el nocontrol
                    String nocontrol = scannerCon.nextLine();

                    sentenciaCon.setString(1, nocontrol);

                    ResultSet rsCon = sentenciaCon.executeQuery();

                    while (rsCon.next()) {

                        System.out.println("nocontrol: " + rsCon.getString("nocontrol") + "     " + "nombre: "
                                + rsCon.getString("nombre") + "     " + "calificicacion: "
                                + rsCon.getInt("calificicacion") + "     " + "fecha: " + rsCon.getDate("fecha"));

                    }

                    System.out.println("\n" + mensaje);
                    opcion = teclado.nextInt();
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Solo números entre 1 y 5");
                }
            } while (!salir);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
