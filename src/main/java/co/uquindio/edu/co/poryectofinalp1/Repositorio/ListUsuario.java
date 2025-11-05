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
    }

    public static ListUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ListUsuario();
        }
        return instancia;
    }

    public Usuario autenticar(String username, String password) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username) &&
                        u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}