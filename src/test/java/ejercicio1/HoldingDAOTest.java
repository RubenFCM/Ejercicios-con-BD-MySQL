package ejercicio1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HoldingDAOTest {

    HoldingDAO dao;

    @BeforeEach
    void setUp() {
        String ruta_scrip = "src/main/java/ejercicio1/script_datos.sql";
        BasesDatos.borrarDatos("holding");
        BasesDatos.volcarDatos(ruta_scrip,"holding");

        dao = new HoldingDAO("localhost", "holding", "root", "");
    }

    @Test
    void agregarEmpleado() {
        dao.agregarEmpleado("Ruben","Fernandez","1993-02-01","desarrollador","ruben@ruben.com","2022-02-28",5600.0,"hola");
    }

    @Test
    void subirSueldo() {
        dao.subirSueldo("Innovns", 890.99);
    }

    @Test
    void trasladarEmpleado() {
        dao.trasladarEmpleado("Juan","CodeCrafters");
    }

    @Test
    void empleadosEmpresa() {
        String esperado ="Juan\n" +
                "Carlos\n";
        String res = dao.empleadosEmpresa("Innovatech Solutions");
        assertEquals(esperado,res);
    }

    @Test
    void crearCoche() {
        dao.crearCoche("Skyline GTR","Nissan",4000.00,2012,"Juan");
    }

    @Test
    void costeProyecto() {
        Double esperado = 6300.0;
        Double res = dao.costeProyecto("CodeFusion");
        assertEquals(esperado,res);
    }

    @Test
    void resumenProyectos() {
        String esperado = "CodeFusion\n" +
                "Fecha: 2023-01-01\n" +
                "Juan\n" +
                "María\n" +
                "Coste: 6300.0\n" +
                "================\n" +
                "FusionWorks\n" +
                "Fecha: 2022-05-15\n" +
                "Carlos\n" +
                "Coste: 4500.0\n" +
                "================\n" +
                "CyberPulse\n" +
                "Fecha: 2023-03-10\n" +
                "Laura\n" +
                "Coste: 3800.0\n" +
                "================\n" +
                "QuantumQuest\n" +
                "Fecha: 2022-11-20\n" +
                "Coste: 0.0\n" +
                "================\n";
        String res =dao.resumenProyectos();
        assertEquals(esperado,res);
    }

    @Test
    void empleadosSinCoche() {
        int esperado = 0;
        int resultado = dao.empleadosSinCoche();
        assertEquals(esperado,resultado);
    }

    @Test
    void borrarProyectosSinEmp() {
        dao.BorrarProyectosSinEmp();
    }

    @Test
    void borrarAño() {
        dao.BorrarAño(2023);
    }
}