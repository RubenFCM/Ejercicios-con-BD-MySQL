package ejercicio2;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.time.LocalDate;

public class ClubDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    Connection conexion = null;
    PreparedStatement stmt = null;
    ResultSet res = null;
    public ClubDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }
    private void cerrarConexion(Connection conexion,PreparedStatement stmt, ResultSet res){
        try {
            if (res != null) res.close();
            if (stmt != null) stmt.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Problemas al cerrar conexión");
        }
    }
    private int buscarEvento(String nombre,Connection conexion){
        int buscado = 0;
        try{
            String select = "SELECT id FROM eventos " +
                    "WHERE nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                buscado = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return buscado;
    }
    private int buscarSocio(String nombre,Connection conexion){
        int buscado = 0;
        try{
            String select = "SELECT id FROM socios " +
                    "WHERE nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                buscado = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return buscado;
    }
    public void crearEvento(String nombre,String fecha){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            int eventoBuscado = buscarEvento(nombre,conexion);
            if (eventoBuscado > 1){
                System.out.println("El evento ya existe");
            }else{
                String insert = "INSERT INTO eventos " +
                        "VALUES(null,?,?)";
                stmt = conexion.prepareStatement(insert);
                stmt.setString(1,nombre);
                stmt.setString(2,fecha);
                stmt.executeUpdate();
                System.out.println("Evento insertado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }

    }

    public void añadirSocio(String nombre){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            int socioBuscado = buscarSocio(nombre,conexion);
            if (socioBuscado > 1){
                System.out.println("El socio ya existe");
            }else{
                LocalDate fecha = LocalDate.now();
                String insert = "INSERT INTO socios " +
                        "VALUES(null,?,?)";
                stmt = conexion.prepareStatement(insert);
                stmt.setString(1,nombre);
                stmt.setString(2,fecha.toString());
                stmt.executeUpdate();
                System.out.println("Socio insertado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }


    public void apuntarseEvento(String socio,String evento){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            int socioBuscadoID = buscarSocio(socio,conexion);
            if (socioBuscadoID < 1){
                System.out.println("El socio no existe");
            }else{
                int eventoBuscadoID = buscarEvento(evento,conexion);
                if (eventoBuscadoID < 1){
                    System.out.println("El evento no existe");
                }else {
                    //Consultar si el socio ya esta apuntado a ese evento
                    String select3 ="SELECT evento FROM Inscripciones WHERE socio IN (SELECT id FROM socios WHERE id =?) AND evento = ?";
                    stmt = conexion.prepareStatement(select3);
                    stmt.setInt(1,socioBuscadoID);
                    stmt.setInt(2,eventoBuscadoID);
                    res = stmt.executeQuery();
                    if (res.next()){
                        System.out.println("El socio ya está inscrito al evento");
                    }else {
                        String insert ="INSERT INTO inscripciones " +
                                "VALUES(null,?,?)";
                        stmt = conexion.prepareStatement(insert);
                        stmt.setInt(1,socioBuscadoID);
                        stmt.setInt(2,eventoBuscadoID);
                        stmt.executeUpdate();
                        System.out.println("Socio inscrito a evento");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public String eventosSocio(String socio){
        String eventos ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            int socioBuscadoID = buscarSocio(socio,conexion);
            if (socioBuscadoID < 1){
                System.out.println("El socio no existe");
            }else{
                    String select ="SELECT nombre FROM eventos JOIN inscripciones ON eventos.id = inscripciones.evento WHERE socio IN (SELECT id FROM socios WHERE nombre =?)";
                    stmt = conexion.prepareStatement(select);
                    stmt.setString(1,socio);
                    res = stmt.executeQuery();
                    while (res.next()){
                        eventos += res.getString("nombre")+"\n";
                    }
                    if (eventos.equals("")) {
                        eventos ="El socio no está apuntado a ningún evento.";
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return eventos;
    }
    private String sociosEventos(String nombre){
        String resultado ="";
        ResultSet newRes = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            String select ="SELECT s.nombre  FROM socios s JOIN inscripciones i ON i.socio = s.id JOIN eventos e ON e.id = i.evento WHERE e.nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            newRes=stmt.executeQuery();
            while (newRes.next()){
                resultado+= newRes.getString(1)+"\n";
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,newRes);
        }

        return resultado;
    }
    public String resumentEventos(){
        String datos ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            String select = "SELECT * FROM eventos";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                String nom_evento =res.getString("nombre");
                datos += nom_evento+"\n" +
                        "Fecha: "+res.getString("fecha")+"\n" +
                        sociosEventos(nom_evento)+
                        "============================\n";
            }
//                String select ="SELECT e.*, s.nombre FROM eventos e JOIN inscripciones i ON e.id = i.evento JOIN socios s ON i.socio = s.id ORDER BY e.fecha";
//                stmt = conexion.prepareStatement(select);
//                res = stmt.executeQuery();
//                while (res.next()){
//                    datos += res.getString(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getString(4)+"\n";
//                }
                if (datos.equals("")){
                    System.out.println("No existen eventos");
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return datos;
    }

    public String valoracionesEvento(String evento){
        String comentarios ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            int eventoID = buscarEvento(evento,conexion);
            if(eventoID < 1){
                System.out.println("No existe ese evento.");
            }else{
                String select ="SELECT comentario FROM resenas_eventos re " +
                        "JOIN eventos e ON re.evento_id = e.id " +
                        "WHERE e.nombre = ?";
                stmt = conexion.prepareStatement(select);
                stmt.setString(1,evento);
                res = stmt.executeQuery();
                while (res.next()){
                    comentarios += res.getString(1)+"\n";            }
                if (comentarios.equals("")){
                    System.out.println("No existen comentarios");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return comentarios;
    }

    public String eventoMultitudinario(){
        String datos ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            String select ="SELECT COUNT(*) total , e.nombre FROM eventos e JOIN inscripciones i ON e.id = i.evento JOIN socios s ON i.socio = s.id GROUP BY e.nombre ORDER BY total DESC LIMIT 1";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                datos += "El evento com mas asistencia ha sido "+res.getString(2)+" con "+res.getString(1)+" asistentes\n";
            }
            if (datos.equals("")){
                System.out.println("No existen eventos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return datos;
    }

    public String sinSocios(){
        String datos ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            String select ="SELECT e.nombre FROM eventos e LEFT JOIN inscripciones i ON i.evento = e.id WHERE i.evento IS NULL;";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                datos += res.getString(1)+"\n";
            }
            if (datos.equals("")){
                System.out.println("No existen eventos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return datos;
    }

    public String mejorValorado(){
        String datos ="";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            String select ="SELECT e.nombre, ROUND(AVG(re.puntuacion),2) Pruntuacion FROM eventos e JOIN resenas_eventos re ON re.evento_id = e.id GROUP BY e.nombre ORDER BY Puntuacion DESC LIMIT 1";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                datos += "El evento mejor valorado es "+res.getString(1)+" con una media de: "+res.getString(2)+"\n";
            }
            if (datos.equals("")){
                System.out.println("No existen eventos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return datos;
    }

    public void borrarEventos(Integer año){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/club", "root", "");
            //Buscar evento por año
            String existe = "SELECT id FROM eventos WHERE YEAR(fecha)=?";
            stmt = conexion.prepareStatement(existe);
            stmt.setInt(1,año);
            res = stmt.executeQuery();
            if (res.next()){
                int eventoID = res.getInt(1);
                String delete ="DELETE FROM eventos WHERE YEAR(fecha) = ?";
                stmt =conexion.prepareStatement(delete);
                stmt.setInt(1,año);
                stmt.executeUpdate();
                String delete2 ="DELETE FROM inscripciones WHERE evento = ?";
                stmt =conexion.prepareStatement(delete2);
                stmt.setInt(1,eventoID);
                stmt.executeUpdate();
                System.out.println("Evento borrado");
            }else {

                System.out.println("No existe evento en ese año");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

}
