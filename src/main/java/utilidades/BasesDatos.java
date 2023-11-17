package utilidades;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class BasesDatos {

    public static void volcarDatos(String ruta_script,String base_datos) {
        Connection conexion = null;
        Statement statement = null;

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/"+base_datos,
                    "root", //usuario de la BD
                    ""); //contraseña

            BufferedReader reader = new BufferedReader(new FileReader(ruta_script));
            statement = conexion.createStatement();
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                statement.executeUpdate(line);
            }
            reader.close();

        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("No existe el fichero\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error del fichero\n" + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void borrarDatos(String base_datos) {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement tablas_statement = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/"+base_datos,
                    "root", //usuario de la BD
                    ""); //contraseña

            statement = conexion.createStatement();


            tablas_statement=conexion.createStatement();
            resultSet = tablas_statement.executeQuery("SHOW TABLES");

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                // Eliminar la tabla
                statement.executeUpdate("DELETE FROM " + tableName);
                System.out.println("Tabla " + tableName + " vaciada.");
            }

        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
                if (resultSet != null) resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
