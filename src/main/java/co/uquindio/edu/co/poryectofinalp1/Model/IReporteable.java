package co.uquindio.edu.co.poryectofinalp1.Model;
/**
 * INTERFAZ - Define comportamientos comunes para entidades que generan reportes
 * Cumple con el requisito de POO del proyecto: "Definir comportamientos comunes a distintas clases"
 *
 * Esta interfaz establece el contrato que deben cumplir todas las entidades
 * que necesiten generar información en formato de reporte.
 */
public interface IReporteable {
    /**
     * Genera un reporte básico en formato texto con la información principal
     * @return String con el reporte formateado en una línea
     */
    String generarReporte();
    /**
     * Genera un reporte detallado con toda la información disponible
     * Incluye más campos y formato multilínea
     * @return String con el reporte detallado formateado
     */
    String generarReporteDetallado();
    /**
     * Obtiene la fecha y hora del último reporte generado
     * Útil para auditoría y seguimiento
     * @return String con la fecha formateada (formato: dd/MM/yyyy HH:mm:ss)
     */
    String obtenerFechaUltimoReporte();
}
