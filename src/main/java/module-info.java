module CUTECAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.CUTECAT.app to javafx.fxml;
    exports com.CUTECAT.diegoutil;
    exports com.CUTECAT.app;
}