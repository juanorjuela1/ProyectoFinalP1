package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * INTERFAZ - Define comportamientos para entidades que requieren autenticación
 * Cumple con el requisito de POO del proyecto: "Definir comportamientos comunes a distintas clases"
 *
 * Esta interfaz establece el contrato para sistemas de seguridad y control de acceso.
 * Puede ser implementada por Usuario, Empleado, o cualquier entidad que requiera
 * validación de credenciales y gestión de permisos.
 */
public interface IAutenticable {

    /**
     * Valida las credenciales proporcionadas contra las almacenadas
     * @param password contraseña a validar
     * @return true si la contraseña es válida, false en caso contrario
     */
    boolean validarCredenciales(String password);

    /**
     * Cambia la contraseña del usuario
     * Requiere la contraseña actual para mayor seguridad
     * @param passwordActual contraseña actual del usuario
     * @param passwordNueva nueva contraseña a establecer
     * @return true si el cambio fue exitoso, false si la contraseña actual es incorrecta
     */
    boolean cambiarPassword(String passwordActual, String passwordNueva);

    /**
     * Verifica si el usuario tiene permisos para realizar una acción específica
     * @param accion nombre de la acción a verificar (ej: "CREAR_CUENTA", "VER_REPORTES")
     * @return true si el usuario tiene permisos, false en caso contrario
     */
    boolean tienePermiso(String accion);
}