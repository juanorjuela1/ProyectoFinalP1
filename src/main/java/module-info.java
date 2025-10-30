module co.uquindio.edu.co.poryectofinalp1 {
    requires javafx.controls;
    requires javafx.fxml;

    // Si usas otras librerías (como java.sql, etc.) puedes agregarlas aquí:
    // requires java.sql;

    opens co.uquindio.edu.co.poryectofinalp1 to javafx.fxml;
    exports co.uquindio.edu.co.poryectofinalp1;
}
