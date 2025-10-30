module co.uquindio.edu.co.poryectofinalp1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.uquindio.edu.co.poryectofinalp1 to javafx.fxml;
    exports co.uquindio.edu.co.poryectofinalp1;

    // IMPORTANTE: Exportar Controller
    opens co.uquindio.edu.co.poryectofinalp1.Controller to javafx.fxml;
    exports co.uquindio.edu.co.poryectofinalp1.Controller;

    opens co.uquindio.edu.co.poryectofinalp1.Model to javafx.fxml;
    exports co.uquindio.edu.co.poryectofinalp1.Model;

    opens co.uquindio.edu.co.poryectofinalp1.Repositorio to javafx.fxml;
    exports co.uquindio.edu.co.poryectofinalp1.Repositorio;
}