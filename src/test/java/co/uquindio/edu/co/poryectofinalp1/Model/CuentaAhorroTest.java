package co.uquindio.edu.co.poryectofinalp1.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PRUEBAS UNITARIAS para CuentaAhorro
 * Verifica el correcto funcionamiento de todos los métodos
 */
class CuentaAhorroTest {

    private CuentaAhorro cuenta;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada test
        cuenta = new CuentaAhorro("Juan Pérez", "123456789", 1000000.0);
    }

    @Test
    void testCreacionCuenta() {
        assertNotNull(cuenta, "La cuenta no debe ser nula");
        assertEquals("Juan Pérez", cuenta.getTitularNombre());
        assertEquals("123456789", cuenta.getTitularCedula());
        assertEquals(1000000.0, cuenta.getSaldo(), 0.01);
        assertTrue(cuenta.estaActiva());
        assertEquals("AHORRO", cuenta.getTipoCuenta());
    }

    @Test
    void testDepositoExitoso() {
        boolean resultado = cuenta.depositar(500000.0);
        assertTrue(resultado, "El depósito debe ser exitoso");
        assertEquals(1500000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testDepositoMontoNegativo() {
        boolean resultado = cuenta.depositar(-100000.0);
        assertFalse(resultado, "No debe permitir depósitos negativos");
        assertEquals(1000000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testRetiroExitoso() {
        boolean resultado = cuenta.retirar(300000.0);
        assertTrue(resultado, "El retiro debe ser exitoso");
        assertEquals(700000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testRetiroSaldoInsuficiente() {
        boolean resultado = cuenta.retirar(2000000.0);
        assertFalse(resultado, "No debe permitir retiros mayores al saldo");
        assertEquals(1000000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testRetiroExcedeLimiteDiario() {
        boolean resultado = cuenta.retirar(1500000.0);
        assertFalse(resultado, "No debe permitir retiros que excedan el límite diario");
        assertEquals(1000000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testConsultarSaldo() {
        double saldo = cuenta.consultarSaldo();
        assertEquals(1000000.0, saldo, 0.01);
    }

    @Test
    void testAplicarIntereses() {
        cuenta.aplicarIntereses();
        // Con tasa del 5%: 1000000 + (1000000 * 0.05) = 1050000
        assertEquals(1050000.0, cuenta.getSaldo(), 0.01);
    }

    @Test
    void testCuentaInactiva() {
        cuenta.setActiva(false);
        boolean resultadoDeposito = cuenta.depositar(100000.0);
        boolean resultadoRetiro = cuenta.retirar(100000.0);

        assertFalse(resultadoDeposito, "No debe permitir depósitos en cuenta inactiva");
        assertFalse(resultadoRetiro, "No debe permitir retiros en cuenta inactiva");
        assertFalse(cuenta.estaActiva());
    }

    @Test
    void testGenerarReporte() {
        String reporte = cuenta.generarReporte();

        assertNotNull(reporte);
        assertTrue(reporte.contains("AHORRO"));
        assertTrue(reporte.contains("Juan Pérez"));
    }

    @Test
    void testGenerarReporteDetallado() {
        String reporte = cuenta.generarReporteDetallado();
        assertNotNull(reporte);
        assertTrue(reporte.contains("Tasa de Interés"));
        assertTrue(reporte.contains("5.00%"));
    }

    @Test
    void testGetLimiteRetiroDiario() {
        assertEquals(1000000.0, cuenta.getLimiteRetiroDiario(), 0.01);
    }

    @Test
    void testNumeroCuentaGenerado() {
        assertNotNull(cuenta.getNumeroCuenta());
        assertTrue(cuenta.getNumeroCuenta().length() > 0);
    }

    @Test
    void testFechaApertura() {
        assertNotNull(cuenta.getFechaApertura());
        assertTrue(cuenta.getFechaApertura().length() > 0);
    }
}