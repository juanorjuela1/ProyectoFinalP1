package co.uquindio.edu.co.poryectofinalp1.Model;

public class Usuario {
    private String username;
    private String password;
    private TipoUsuario tipo;

    public Usuario() {}

    public Usuario(String username, String password, TipoUsuario tipo) {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        CAJERO
    }
}