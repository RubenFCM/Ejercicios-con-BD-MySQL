package ejercicio3;

import java.sql.*;
import java.time.LocalDate;

public class TiendaDAO {
    private String host;
    private String base_datos;
    private String usuario;
    private String password;
    private Connection conexion;
    private PreparedStatement stmt;
    private ResultSet res;

    public TiendaDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }
    private int buscaProductoID(String nombre,Connection conexion){
        int id = 0;
        try{
            String select = "SELECT id FROM productos WHERE nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    private int buscarClienteID(String nombre,Connection conexion){
        int id = 0;
        try{
            String select = "SELECT id FROM clientes WHERE nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (res.next()){
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
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
    public void añadirVenta(String cliente,String producto, Integer ventas){
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            int clienteID = buscarClienteID(cliente,conexion);
            if (clienteID < 1){
                System.out.println("No existe ese cliente");
            }else {
                int productoID = buscaProductoID(producto,conexion);
                if (productoID<1){
                    System.out.println("No existe el producto");
                }else{
                    LocalDate fecha = LocalDate.now();
                    String insert = "INSERT INTO ventas " +
                            "VALUES(null,?,?,?,?)";
                    stmt = conexion.prepareStatement(insert);
                    stmt.setInt(1,productoID);
                    stmt.setInt(2,clienteID);
                    stmt.setString(3,fecha.toString());
                    stmt.setInt(4,ventas);
                    stmt.executeUpdate();
                    System.out.println("Venta realizada");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

    public String comprasCliente(String cliente){
        String compras = "";
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            int clienteID = buscarClienteID(cliente,conexion);
            if (clienteID<1){
                System.out.println("El cliente no existe");
            }else {
                String select ="SELECT p.nombre , v.unidades FROM ventas v JOIN productos p ON p.id = v.producto WHERE v.cliente = ?";
                stmt= conexion.prepareStatement(select);
                stmt.setInt(1,clienteID);
                res = stmt.executeQuery();
                while (res.next()){
                    compras += res.getString(1)+" "+res.getString(2)+"\n";
                }
                if (compras.equals("")){
                    compras ="El cliente no ha realizado ninguna compra";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return compras;
    }

    public Double recaudacionTotal(){
        double ganancia = 0.0;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            String select = "SELECT SUM(total) Ganancia FROM (SELECT SUM(v.unidades) unidades ,SUM(v.unidades*p.precio) total FROM ventas v JOIN productos p ON p.id = v.producto GROUP BY p.nombre) subconsulta";
            stmt=conexion.prepareStatement(select);
            res = stmt.executeQuery();
            if (res.next()){
                ganancia = res.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return ganancia;
    }


    public String porCategorias(){
        String resultado = "";
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            String select = "SELECT c.nombre, COUNT(v.id) Total FROM categorias c LEFT JOIN productos p ON p.categoria_id = c.id LEFT JOIN ventas v ON v.producto = p.id GROUP BY c.nombre";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                resultado += "Categoria: "+res.getString(1)+" | Total de ventas: "+res.getString(2)+"\n";
            }
            if (resultado.equals("")){
                resultado ="No se ha encontrado ninguna venta por categoría";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return  resultado;
    }

    public String ultimaVenta(){
        String resultado = "";
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            String select = "SELECT c.nombre, p.nombre FROM clientes c JOIN ventas v ON v.cliente = c.id JOIN productos p ON p.id = v.producto ORDER BY v.fecha DESC LIMIT 1";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            if (res.next()){
                resultado = "Cliente: "+res.getString(1)+" | Producto: "+res.getString(2);
            }
            else {
                resultado ="No se ha encontrado ninguna venta";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return  resultado;
    }

    public String masVendido(){
        String resultado = "";
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            String select = "SELECT p.nombre, COALESCE(SUM(v.unidades),0) total FROM productos p LEFT JOIN ventas v ON v.producto = p.id GROUP BY p.nombre ORDER BY total DESC LIMIT 1";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            if (res.next()){
                resultado = "Producto: "+res.getString(1)+" | Total: "+res.getInt(2);
            }
            else {
                resultado ="No se ha encontrado productos vendidos";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return  resultado;
    }

    public String sinVentas(){
        String resultado = "";
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            String select = "SELECT p.nombre FROM productos p LEFT JOIN ventas v ON v.producto = p.id WHERE v.unidades IS NULL";
            stmt = conexion.prepareStatement(select);
            res = stmt.executeQuery();
            while (res.next()){
                resultado += "Producto: "+res.getString(1)+"\n";
            }
            if (resultado.equals("")) {
                resultado ="No se ha encontrado productos sin venta";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
        return  resultado;
    }


    public void borrarProveedor(String nombre){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            //Buscar si existe el proveedor

            String select = "SELECT id FROM proveedores WHERE nombre = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setString(1,nombre);
            res = stmt.executeQuery();
            if (!res.next()){
                System.out.println("No existe ese proveedor");
            }else{
                int provID = res.getInt(1);
                //Borrar productos del proveedor
                String deleteProducto ="DELETE FROM productos WHERE proveedor_id = ?";
                stmt = conexion.prepareStatement(deleteProducto);
                stmt.setInt(1,provID);
                stmt.executeUpdate();
                //Borrar proveedor
                String deleteProv ="DELETE FROM proveedores WHERE nombre = ?";
                stmt = conexion.prepareStatement(deleteProv);
                stmt.setString(1,nombre);
                stmt.executeUpdate();
                System.out.println("Proveedor y sus productos borrados");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            cerrarConexion(conexion,stmt,res);
        }
    }

}
