package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListUsuario {
    private static ListUsuario instancia;
    private List<Usuario> usuarios;

    private ListUsuario() {
        usuarios = new ArrayList<>();
        // Crear usuarios predeterminados
        usuarios.add(new Usuario("admin", "admin123", Usuario.TipoUsuario.ADMIN));
        usuarios.add(new Usuario("cliente", "cliente123", Usuario.TipoUsuario.CLIENTE));
        usuarios.add(new Usuario("cajero", "cajero123", Usuario.TipoUsuario.CAJERO));

        // Debug: imprimir usuarios creados
        System.out.println("=== USUARIOS REGISTRADOS ===");
        for (Usuario u : usuarios) {
            System.out.println("Usuario: " + u.getUsername() + " - Tipo: " + u.getTipo());
        }
    }

    public static ListUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ListUsuario();
        }
        return instancia;
    }

    public Usuario autenticar(String username, String password) {
        System.out.println("Intentando autenticar: " + username);
        Usuario user = usuarios.stream()
                .filter(u -> u.getUsername().equals(username) &&
                        u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            System.out.println("✓ Usuario autenticado: " + user.getUsername() + " - Tipo: " + user.getTipo());
        } else {
            System.out.println("✗ Autenticación fallida");
        }
        return user;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}