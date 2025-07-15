module com.CUTECAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
  
    exports com.CUTECAT;
  
    opens com.CUTECAT.app to javafx.fxml;
    exports com.CUTECAT.diegoutil;
    exports com.CUTECAT.app;
    exports com.CUTECAT.modes;
    exports com.CUTECAT.modes.capabilities;
}