package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Usuario;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private Usuario usuarioActual;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Sesión iniciada para: " + usuario.getUsername() + " - Rol: " + usuario.getTipo());
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean estaLogueado() {
        return usuarioActual != null;
    }

    public boolean esAdmin() {
        return usuarioActual != null &&
                usuarioActual.getTipo() == Usuario.TipoUsuario.ADMIN;
    }

    public boolean esCliente() {
        return usuarioActual != null &&
                usuarioActual.getTipo() == Usuario.TipoUsuario.CLIENTE;
    }

    public boolean esCajero() {
        return usuarioActual != null &&
                usuarioActual.getTipo() == Usuario.TipoUsuario.CAJERO;
    }
}