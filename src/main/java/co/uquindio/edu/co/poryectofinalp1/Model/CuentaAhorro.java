package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * CuentaAhorro - HERENCIA de CuentaBancaria
 * Diseñada para clientes que buscan acumular intereses sobre su saldo
 * Aplica POLIMORFISMO
 *
 * Características especiales:
 * - Genera intereses mensuales
 * - Límite de retiro diario: $1,000,000
 * - Tasa de interés del 5% anual
 */
public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;
    private static final double LIMITE_RETIRO_DIARIO = 1000000.0;
    private static final double TASA_INTERES_DEFAULT = 0.05; // 5%

    public CuentaAhorro() {
        super();
        this.tasaInteres = TASA_INTERES_DEFAULT;
    }

    public CuentaAhorro(String titularNombre, String titularCedula, double saldoInicial) {
        super(titularNombre, titularCedula, saldoInicial);
        this.tasaInteres = TASA_INTERES_DEFAULT;
    }

    public CuentaAhorro(String titularNombre, String titularCedula, double saldoInicial, double tasaInteres) {
        super(titularNombre, titularCedula, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public String getTipoCuenta() {
        return "AHORRO";
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public double getLimiteRetiroDiario() {
        return LIMITE_RETIRO_DIARIO;
    }

    /**
     * Calcula y aplica intereses al saldo
     * Este método es específico de las cuentas de ahorro
     */
    public void aplicarIntereses() {
        if (activa && saldo > 0) {
            double intereses = saldo * tasaInteres;
            this.saldo += intereses;
        }
    }

    /**
     * Calcula cuánto generaría de intereses sin aplicarlos
     */
    public double calcularIntereses() {
        if (saldo > 0) {
            return saldo * tasaInteres;
        }
        return 0;
    }

    /**
     * POLIMORFISMO - Sobrescribe para aplicar límite de retiro
     */
    @Override
    public boolean retirar(double monto) {
        if (monto > LIMITE_RETIRO_DIARIO) {
            return false; // Excede límite diario
        }
        return super.retirar(monto);
    }

    /**
     * POLIMORFISMO - Reporte específico para cuenta de ahorro
     */
    @Override
    public String generarReporteDetallado() {
        return super.generarReporteDetallado() +
                String.format(java.util.Locale.US, "\nTasa de Interés: %.2f%%\nIntereses proyectados: $%.2f",
                        tasaInteres * 100, calcularIntereses());
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }


    public static double getTasaInteresDefault() {
        return TASA_INTERES_DEFAULT;
    }
}