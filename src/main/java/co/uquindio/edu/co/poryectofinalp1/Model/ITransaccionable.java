package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * INTERFAZ - Define comportamientos comunes para entidades que pueden realizar transacciones
 * Cumple con el requisito de POO del proyecto
 */
public interface ITransaccionable {
    /**
     * Realiza un depósito en la cuenta
     * @param monto cantidad a depositar
     * @return true si fue exitoso
     */
    boolean depositar(double monto);

    /**
     * Realiza un retiro de la cuenta
     * @param monto cantidad a retirar
     * @return true si fue exitoso
     */
    boolean retirar(double monto);

    /**
     * Consulta el saldo disponible
     * @return saldo actual
     */
    double consultarSaldo();

    /**
     * Verifica si la cuenta está activa
     * @return true si está activa
     */
    boolean estaActiva();
}