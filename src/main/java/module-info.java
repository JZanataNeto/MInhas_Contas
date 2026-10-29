module com.joaozanata.minhas_contas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.joaozanata.minhas_contas to javafx.fxml;
    exports com.joaozanata.minhas_contas;
}