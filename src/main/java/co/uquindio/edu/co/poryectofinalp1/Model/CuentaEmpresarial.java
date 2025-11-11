package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * CuentaEmpresarial - HERENCIA de CuentaBancaria
 * Diseñada para empresas con movimientos de alto volumen
 * Aplica POLIMORFISMO
 *
 * Características especiales:
 * - Límite de retiro diario: $50,000,000
 * - 100 transacciones gratuitas al mes
 * - Después de 100 transacciones: $5,000 por transacción
 * - Almacena información de empresa (NIT, Razón Social)
 */
public class CuentaEmpresarial extends CuentaBancaria {
    private String razonSocial;
    private String nit;
    private int cantidadTransaccionesMes;
    private static final double LIMITE_RETIRO_DIARIO = 50000000.0;
    private static final int TRANSACCIONES_GRATIS = 100;
    private static final double COSTO_TRANSACCION = 5000.0;

    public CuentaEmpresarial() {
        super();
        this.cantidadTransaccionesMes = 0;
    }

    public CuentaEmpresarial(String razonSocial, String nit, double saldoInicial) {
        super(razonSocial, nit, saldoInicial);
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.cantidadTransaccionesMes = 0;
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public String getTipoCuenta() {
        return "EMPRESARIAL";
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public double getLimiteRetiroDiario() {
        return LIMITE_RETIRO_DIARIO;
    }

    /**
     * Calcula el costo de transacción si excede el límite gratuito
     */
    private double calcularCostoTransaccion() {
        if (cantidadTransaccionesMes >= TRANSACCIONES_GRATIS) {
            return COSTO_TRANSACCION;
        }
        return 0.0;
    }

    /**
     * Calcula cuántas transacciones gratuitas quedan este mes
     */
    public int getTransaccionesGratisRestantes() {
        int restantes = TRANSACCIONES_GRATIS - cantidadTransaccionesMes;
        return Math.max(0, restantes);
    }

    /**
     * Verifica si la próxima transacción tendrá costo
     */
    public boolean proximaTransaccionTieneCosto() {
        return cantidadTransaccionesMes >= TRANSACCIONES_GRATIS;
    }

    /**
     * POLIMORFISMO - Sobrescribe para cobrar comisión si aplica
     */
    @Override
    public boolean retirar(double monto) {
        if (monto <= 0 || !activa || monto > LIMITE_RETIRO_DIARIO) {
            return false;
        }

        double costoTransaccion = calcularCostoTransaccion();
        double montoTotal = monto + costoTransaccion;

        if (this.saldo >= montoTotal) {
            this.saldo -= montoTotal;
            cantidadTransaccionesMes++;
            return true;
        }
        return false;
    }

    /**
     * POLIMORFISMO - Sobrescribe para contar transacciones
     */
    @Override
    public boolean depositar(double monto) {
        if (monto <= 0 || !activa) {
            return false;
        }

        double costoTransaccion = calcularCostoTransaccion();
        this.saldo += monto;

        // Cobra la comisión si aplica
        if (costoTransaccion > 0 && this.saldo >= costoTransaccion) {
            this.saldo -= costoTransaccion;
        }

        cantidadTransaccionesMes++;
        return true;
    }

    /**
     * Reinicia el contador de transacciones mensuales
     * Este método debe llamarse al inicio de cada mes
     */
    public void reiniciarContadorMensual() {
        this.cantidadTransaccionesMes = 0;
    }

    /**
     * Calcula el total de comisiones pagadas este mes
     */
    public double getComisionesPagadasMes() {
        int transaccionesCobradas = Math.max(0, cantidadTransaccionesMes - TRANSACCIONES_GRATIS);
        return transaccionesCobradas * COSTO_TRANSACCION;
    }

    /**
     * POLIMORFISMO - Reporte específico para cuenta empresarial
     */
    @Override
    public String generarReporteDetallado() {
        return super.generarReporteDetallado() +
                String.format("\n=== INFORMACIÓN EMPRESARIAL ===\n" +
                                "Razón Social: %s\n" +
                                "NIT: %s\n" +
                                "Transacciones este mes: %d\n" +
                                "Transacciones gratuitas restantes: %d\n" +
                                "Costo por transacción adicional: $%.2f\n" +
                                "Total comisiones pagadas este mes: $%.2f\n" +
                                "Próxima transacción: %s",
                        razonSocial, nit, cantidadTransaccionesMes,
                        getTransaccionesGratisRestantes(),
                        COSTO_TRANSACCION,
                        getComisionesPagadasMes(),
                        (proximaTransaccionTieneCosto() ? "CON COSTO" : "GRATUITA"));
    }

    /**
     * Genera un reporte fiscal específico para empresas
     */
    public String generarReporteFiscal() {
        return String.format(
                "=== REPORTE FISCAL ===\n" +
                        "NIT: %s\n" +
                        "Razón Social: %s\n" +
                        "Cuenta: %s\n" +
                        "Saldo Actual: $%.2f\n" +
                        "Total Transacciones Mes: %d\n" +
                        "Comisiones Bancarias Mes: $%.2f\n" +
                        "Fecha: %s",
                nit, razonSocial, numeroCuenta, saldo,
                cantidadTransaccionesMes, getComisionesPagadasMes(),
                obtenerFechaUltimoReporte()
        );
    }

    // Getters y Setters específicos
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getCantidadTransaccionesMes() {
        return cantidadTransaccionesMes;
    }

    public void setCantidadTransaccionesMes(int cantidadTransaccionesMes) {
        this.cantidadTransaccionesMes = cantidadTransaccionesMes;
    }

    public static double getCostoTransaccion() {
        return COSTO_TRANSACCION;
    }

    public static int getTransaccionesGratis() {
        return TRANSACCIONES_GRATIS;
    }
}