package ejercicio1;

import java.sql.*;

public class HoldingDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    Connection conexion = null;
    PreparedStatement stmt = null;
    ResultSet res = null;
    public HoldingDAO(String host, String base_datos, String usuario, String password) {
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
    private String buscarEmpleadoEmail(String email, Connection conexion){
        String buscado="";
        try {
            String select = "SELECT email FROM empleados " +
                    "WHERE email = ?";
            stmt=conexion.prepareStatement(select);
            stmt.setString(1,email);
            res = stmt.executeQuery();
            if (res.next()){
                buscado = res.getString("email");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error de SQL");
        }
        return buscado;
    }
    private int buscarIdEmpleadoNombre(String nombre, Connection conexion){
        int buscado=0;
        try {
            String select = "SELECT id FROM empleados " +
                    "WHERE nombre = ?";
            stmt=conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                buscado = res.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error de SQL");
        }
        return buscado;
    }
    private String buscarEmpresa(String nombre, Connection conexion){
        String buscado="";
        try {
            String select = "SELECT razon_social FROM empresas " +
                    "WHERE razon_social = ?";
            stmt=conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                buscado = res.getString("razon_social");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error de SQL");
        }
        return buscado;
    }
    public void agregarEmpleado(String nombre,String apellidos,String fecha_nacimiento,String categoria,String email,String contratacion,Double salario,String empresa){
        try{
            conexion= DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String empleadoBuscado = buscarEmpleadoEmail(email,conexion);
            String empresaBuscada = buscarEmpresa(empresa,conexion);
            if(empleadoBuscado.equalsIgnoreCase("")){
                if (empresaBuscada.equalsIgnoreCase(empresa)){
                    //Traemos la id de la empresa
                    String idEmpresa= "SELECT id FROM empresas WHERE razon_social = ?";
                    stmt = conexion.prepareStatement(idEmpresa);
                    stmt.setString(1,empresa);
                    res = stmt.executeQuery();
                    int id =0;
                    if(res.next()){
                        id = res.getInt("id");
                    }
                    //Insertamos el empleado una vez tenemos el id de la empresa
                    String insert="INSERT INTO empleados " +
                            "VALUES(null,?,?,?,?,?,?,?,?)";
                    stmt = conexion.prepareStatement(insert);
                    stmt.setString(1,fecha_nacimiento);
                    stmt.setString(2,categoria);
                    stmt.setString(3,email);
                    stmt.setString(4,nombre);
                    stmt.setString(5,apellidos);
                    stmt.setString(6,contratacion);
                    stmt.setDouble(7,salario);
                    stmt.setInt(8,id);
                    stmt.executeUpdate();
                    System.out.println("Empleado insertado");
                }else {
                    System.out.println("La empresa no existe");
                }
            }else {
                System.out.println("El empleado ya existe");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }finally {
           cerrarConexion(conexion,stmt,res);
        }
    }

    public void subirSueldo(String empresa,Double subida){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            //Traemos la id de la empresa
            String idEmpresa= "SELECT id FROM empresas WHERE razon_social = ?";
            stmt = conexion.prepareStatement(idEmpresa);
            stmt.setString(1,empresa);
            res = stmt.executeQuery();

            if(res.next()){
                int id = res.getInt("id");

            String update ="UPDATE empleados " +
                    "SET salario = salario + ?" +
                    "WHERE empresa_id = ?";
            stmt = conexion.prepareStatement(update);
            stmt.setDouble(1,subida);
            stmt.setInt(2,id);
            stmt.executeUpdate();
            System.out.println("Sueldos actualizados");
            }else{
                System.out.println("Empresa no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public void trasladarEmpleado(String empleado,String empresa){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String selectEmpleado = "SELECT nombre FROM empleados WHERE nombre = ?";
            stmt=conexion.prepareStatement(selectEmpleado);
            stmt.setString(1,empleado);
            res = stmt.executeQuery();
            if (res.next()){
                //Traemos la id de la empresa
                String idEmpresa= "SELECT id FROM empresas WHERE razon_social = ?";
                stmt = conexion.prepareStatement(idEmpresa);
                stmt.setString(1,empresa);
                res = stmt.executeQuery();
                if(res.next()){
                    int id = res.getInt("id");
                    String update = "UPDATE empleados " +
                            "SET empresa_id =? " +
                            "WHERE nombre = ?";
                    stmt = conexion.prepareStatement(update);
                    stmt.setInt(1,id);
                    stmt.setString(2,empleado);
                    stmt.executeUpdate();
                    System.out.println("El empleado ha sido cambiado de empresa");
                }else{
                    System.out.println("La empresa a la que se quiere cambiar no exite");
                }
            }else {
                System.out.println("Ese empleado no existe");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public String empleadosEmpresa(String empresa){
        String empleados= "";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String empresaBuscada= buscarEmpresa(empresa,conexion);
            if (!empresaBuscada.isEmpty()){
                String select ="SELECT nombre FROM empleados "+
                        "JOIN empresas e ON e.id = empleados.empresa_id "+
                        "WHERE e.razon_social = ?";
                stmt = conexion.prepareStatement(select);
                stmt.setString(1,empresa);
                res = stmt.executeQuery();
                while (res.next()){
                    empleados += res.getString("nombre")+"\n";
                }

            }else{
                System.out.println("Empresa no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return empleados;
    }

    public void crearCoche(String modelo,String fabricante,Double cc,Integer año,String empleado){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            int idEmp = buscarIdEmpleadoNombre(empleado,conexion);
            if (idEmp<1){
                System.out.println("El empleado no existe");
            }else {
                String insert = "INSERT INTO coches " +
                        "VALUES(null,?,?,?,?,?)";
                stmt = conexion.prepareStatement(insert);
                stmt.setDouble(1,cc);
                stmt.setString(2,fabricante);
                stmt.setString(3,modelo);
                stmt.setInt(4,año);
                stmt.setInt(5,idEmp);
                stmt.executeUpdate();
                System.out.println("Coche insertado");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public Double costeProyecto(String proyecto){
        double coste = 0.0;
        ResultSet newRes = null;
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");

            String select ="SELECT pr.titulo, COALESCE(SUM(salario),0) as total FROM proyectos pr "+
                "LEFT JOIN empleados_proyectos ep ON pr.id = ep.proyecto_id "+
                "LEFT JOIN empleados e ON ep.empleado_id = e.id "+
                "WHERE pr.titulo = ?";
            stmt =conexion.prepareStatement(select);
            stmt.setString(1,proyecto);
            newRes = stmt.executeQuery();
            if (newRes.next()){
                coste = newRes.getDouble(2);
            }else {
                System.out.println("Ese proyecto no existe");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,newRes);
        }
        return coste;
    }
    private String empleadosProyectos(String titulo){
        String nombre = "";
        ResultSet newRes = null;
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");

            String select ="SELECT nombre FROM empleados e JOIN empleados_proyectos ep ON e.id = ep.empleado_id JOIN proyectos p ON p.id = ep.proyecto_id WHERE p.titulo =?";
            stmt =conexion.prepareStatement(select);
            stmt.setString(1,titulo);
            newRes = stmt.executeQuery();
            while (newRes.next()) {
                nombre += newRes.getString("nombre")+"\n";
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,newRes);
        }
        return nombre;
    }
    public String resumenProyectos(){
        String resumen = "";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            //metodo DANI
            String sql ="SELECT * FROM proyectos";
            stmt = conexion.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()){
                String titulo = res.getString("titulo");
                resumen += titulo +
                        "\nFecha: "+ res.getString("comienzo")+"\n"+
                        empleadosProyectos(titulo)+
                        "Coste: "+ costeProyecto(titulo)+
                         "\n================\n";
            }

//            String select ="SELECT pr.titulo, e.nombre FROM proyectos pr "+
//                    "LEFT JOIN empleados_proyectos ep ON pr.id = ep.proyecto_id "+
//                    "LEFT JOIN empleados e ON ep.empleado_id = e.id "+
//                    "GROUP BY e.nombre ORDER BY pr.titulo";
//            stmt = conexion.prepareStatement(select);
//            res = stmt.executeQuery();
//                while(res.next()){
//                    String proyecto = res.getString(1);
//                    resumen += res.getString(1)+" "+ res.getString(2)+" "+costeProyecto(proyecto)+"\n";
//                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return resumen;
    }

    public Integer empleadosSinCoche(){
        int sinCoche=0;
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String select ="SELECT COUNT(*) EmpleadosSinCoche FROM empleados e " +
                    "LEFT JOIN coches c ON e.id = c.empleado_id " +
                    "WHERE c.empleado_id IS null";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            if(res.next()){
                sinCoche = res.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return sinCoche;
    }

    public void BorrarProyectosSinEmp(){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String select ="SELECT pr.titulo FROM proyectos pr " +
                    "LEFT JOIN empleados_proyectos ep ON pr.id = ep.proyecto_id " +
                    "LEFT JOIN empleados e ON ep.empleado_id = e.id " +
                    "WHERE e.nombre IS NULL";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            int contador = 0;
            while (res.next()){
                String titulo = res.getString(1);
                String delete = "DELETE FROM proyectos " +
                        "WHERE titulo = ?";
                stmt = conexion.prepareStatement(delete);
                stmt.setString(1,titulo);
                stmt.executeUpdate();
                contador++;
            }
            if (contador>0){
                System.out.println("Proyectos borrados : "+contador);
            }else {
                System.out.println("No hay proyectos que no estén asignados a un empleado");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public void BorrarAño(Integer año){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/holding", "root", "");
            String select = "SELECT id FROM proyectos "+
                    "WHERE YEAR(comienzo) = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setInt(1,año);
            res = stmt.executeQuery();
            int n =0;
            while (res.next()){
                int id = res.getInt(1);
                String deleteRelacion = "DELETE FROM empleados_proyectos "+
                        "WHERE proyecto_id = ?";
                stmt = conexion.prepareStatement(deleteRelacion);
                stmt.setInt(1,id);
                stmt.executeUpdate();

                String delete = "DELETE FROM proyectos "+
                        "WHERE YEAR(comienzo) = ?";
                stmt = conexion.prepareStatement(delete);
                stmt.setInt(1,año);
                n+=stmt.executeUpdate();
            }
            System.out.println("Proyectos borrados "+n);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }

    }
}
