package ejercicio2;

import ejercicio1.HoldingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.*;

class ClubDAOTest {


    ClubDAO dao;
    @BeforeEach
    void setUp() {
        String ruta_scrip = "src/main/java/ejercicio2/script_datos.sql";
        BasesDatos.borrarDatos("club");
        BasesDatos.volcarDatos(ruta_scrip,"club");

        dao = new ClubDAO("localhost", "club", "root", "");

    }

    @Test
    void crearEvento() {
        dao.crearEvento("Huelga","2023-11-12");
    }

    @Test
    void añadirSocio() {
        dao.añadirSocio("Ruben");
    }

    @Test
    void apuntarseEvento() {
        dao.apuntarseEvento("Juan","Fiesta de la espuma");
    }

    @Test
    void eventosSocio() {
        String esperado = "Fiesta de la espuma\n" +
                "Cata de vinos\n";
        String res = dao.eventosSocio("Juan");
        assertEquals(esperado,res);
    }

    @Test
    void sociosEvento() {

    }

    @Test
    void resumentEventos() {
        String esperado ="Fiesta de la espuma\n" +
                "Fecha: 2021-12-20\n" +
                "Juan\n" +
                "Antonio\n" +
                "============================\n" +
                "Cata de vinos\n" +
                "Fecha: 2020-11-30\n" +
                "Juan\n" +
                "Jose\n" +
                "============================\n" +
                "Maraton de cine\n" +
                "Fecha: 2022-05-30\n" +
                "============================\n" +
                "Partido de futbol sala\n" +
                "Fecha: 2023-06-30\n" +
                "Antonio\n" +
                "============================\n";

        String res =dao.resumentEventos();
        assertEquals(esperado,res);
    }

    @Test
    void valoracionesEvento() {
        String esperado ="Gran evento, mucha diversión\n" +
                "Divertido pero podría haber más actividades\n";

        String res =dao.valoracionesEvento("Fiesta de la espuma");
        assertEquals(esperado,res);
    }

    @Test
    void eventoMultitudinario() {
        String esperado="El evento com mas asistencia ha sido Fiesta de la espuma con 2 asistentes\n";
        String res =dao.eventoMultitudinario();
        assertEquals(esperado,res);
    }

    @Test
    void sinSocios() {
        String esperado ="Maraton de cine\n";
        String res =dao.sinSocios();
        assertEquals(esperado,res);
    }

    @Test
    void mejorValorado() {
        String esperado ="El evento mejor valorado es Partido de futbol sala con una media de: 5.00\n";
        String res = dao.mejorValorado();
        assertEquals(esperado,res);
    }

    @Test
    void borrarEventos() {
        dao.borrarEventos(2020);
    }
}