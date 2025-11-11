package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * CuentaCorriente - HERENCIA de CuentaBancaria
 * Diseñada para operaciones diarias con posibilidad de sobregiro
 * Aplica POLIMORFISMO
 *
 * Características especiales:
 * - Permite sobregiro (usar más dinero del disponible)
 * - Límite de retiro diario: $5,000,000
 * - Sobregiro por defecto: $500,000
 * - Los depósitos primero pagan el sobregiro utilizado
 */
public class CuentaCorriente extends CuentaBancaria {
    private double sobregiroPermitido;
    private double sobregiroUtilizado;
    private static final double LIMITE_RETIRO_DIARIO = 5000000.0;
    private static final double SOBREGIRO_DEFAULT = 500000.0;

    public CuentaCorriente() {
        super();
        this.sobregiroPermitido = SOBREGIRO_DEFAULT;
        this.sobregiroUtilizado = 0;
    }

    public CuentaCorriente(String titularNombre, String titularCedula, double saldoInicial) {
        super(titularNombre, titularCedula, saldoInicial);
        this.sobregiroPermitido = SOBREGIRO_DEFAULT;
        this.sobregiroUtilizado = 0;
    }

    public CuentaCorriente(String titularNombre, String titularCedula, double saldoInicial, double sobregiroPermitido) {
        super(titularNombre, titularCedula, saldoInicial);
        this.sobregiroPermitido = sobregiroPermitido;
        this.sobregiroUtilizado = 0;
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public String getTipoCuenta() {
        return "CORRIENTE";
    }

    /**
     * POLIMORFISMO - Implementación específica
     */
    @Override
    public double getLimiteRetiroDiario() {
        return LIMITE_RETIRO_DIARIO;
    }

    /**
     * POLIMORFISMO - Sobrescribe para permitir sobregiro
     * Esta es la característica principal de una cuenta corriente
     */
    @Override
    public boolean retirar(double monto) {
        if (monto <= 0 || !activa || monto > LIMITE_RETIRO_DIARIO) {
            return false;
        }

        // Calcula cuánto dinero tiene disponible en total (saldo + sobregiro disponible)
        double saldoDisponible = this.saldo + (sobregiroPermitido - sobregiroUtilizado);

        if (monto <= saldoDisponible) {
            if (monto <= this.saldo) {
                // Retiro normal, no necesita sobregiro
                this.saldo -= monto;
            } else {
                // Necesita usar sobregiro
                double usarSobregiro = monto - this.saldo;
                this.saldo = 0;
                this.sobregiroUtilizado += usarSobregiro;
            }
            return true;
        }
        return false;
    }

    /**
     * POLIMORFISMO - Sobrescribe para manejar sobregiro
     * Los depósitos primero pagan el sobregiro utilizado
     */
    @Override
    public boolean depositar(double monto) {
        if (monto <= 0 || !activa) {
            return false;
        }

        // Primero paga el sobregiro si hay
        if (sobregiroUtilizado > 0) {
            if (monto >= sobregiroUtilizado) {
                // El depósito cubre todo el sobregiro y queda algo para el saldo
                monto -= sobregiroUtilizado;
                sobregiroUtilizado = 0;
                this.saldo += monto;
            } else {
                // El depósito solo alcanza para pagar parte del sobregiro
                sobregiroUtilizado -= monto;
            }
        } else {
            // No hay sobregiro, todo va al saldo
            this.saldo += monto;
        }
        return true;
    }

    /**
     * POLIMORFISMO - Consulta saldo incluyendo sobregiro disponible
     * Retorna el dinero total que puede usar el cliente
     */
    @Override
    public double consultarSaldo() {
        return this.saldo + (sobregiroPermitido - sobregiroUtilizado);
    }

    /**
     * Consulta solo el saldo real (sin contar el sobregiro)
     * Útil para saber cuánto dinero tiene realmente
     */
    public double consultarSaldoReal() {
        return this.saldo;
    }

    /**
     * Calcula cuánto sobregiro tiene disponible todavía
     */
    public double getSobregiroDisponible() {
        return sobregiroPermitido - sobregiroUtilizado;
    }

    /**
     * Verifica si está usando sobregiro actualmente
     */
    public boolean estaEnSobregiro() {
        return sobregiroUtilizado > 0;
    }

    /**
     * POLIMORFISMO - Reporte específico para cuenta corriente
     */
    @Override
    public String generarReporteDetallado() {
        return super.generarReporteDetallado() +
                String.format("\nSobregiro Permitido: $%.2f\n" +
                                "Sobregiro Utilizado: $%.2f\n" +
                                "Sobregiro Disponible: $%.2f\n" +
                                "Saldo Real: $%.2f\n" +
                                "Saldo Total Disponible: $%.2f\n" +
                                "Estado Sobregiro: %s",
                        sobregiroPermitido, sobregiroUtilizado,
                        getSobregiroDisponible(), consultarSaldoReal(),
                        consultarSaldo(),
                        (estaEnSobregiro() ? "EN SOBREGIRO" : "NORMAL"));
    }

    // Getters y Setters específicos
    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }

    public void setSobregiroPermitido(double sobregiroPermitido) {
        this.sobregiroPermitido = sobregiroPermitido;
    }

    public double getSobregiroUtilizado() {
        return sobregiroUtilizado;
    }

    public void setSobregiroUtilizado(double sobregiroUtilizado) {
        this.sobregiroUtilizado = sobregiroUtilizado;
    }

    public static double getSobregiroDefault() {
        return SOBREGIRO_DEFAULT;
    }
}