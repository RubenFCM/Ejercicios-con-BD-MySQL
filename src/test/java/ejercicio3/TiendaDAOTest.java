package ejercicio3;

import ejercicio2.ClubDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.*;

class TiendaDAOTest {

    TiendaDAO dao;

    @BeforeEach
    void setUp() {

        String ruta_scrip = "src/main/java/ejercicio3/script_datos.sql";
        BasesDatos.borrarDatos("tienda");
        BasesDatos.volcarDatos(ruta_scrip,"tienda");

        dao = new TiendaDAO("localhost", "tienda", "root", "");
    }

    @Test
    void añadirVenta() {
        dao.añadirVenta("Paco Menendez","Barra de pan",2);
    }

    @Test
    void comprasCliente() {
        System.out.println(dao.comprasCliente("Paco Menendez"));
    }

    @Test
    void recaudacionTotal() {
        System.out.println("La ganancia total es de : " + dao.recaudacionTotal()+" €");
    }

    @Test
    void porCategorias() {
        System.out.println(dao.porCategorias());
    }

    @Test
    void ultimaVenta() {
        System.out.println(dao.ultimaVenta());
    }

    @Test
    void masVendido() {
        System.out.println(dao.masVendido());
    }

    @Test
    void sinVentas() {
        System.out.println(dao.sinVentas());
    }

    @Test
    void borrarProveedor() {
        dao.borrarProveedor("EcoSolutions Ltd.");
    }
}