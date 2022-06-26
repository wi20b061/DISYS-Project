module com.project.echarging_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;


    opens com.project.echarging_javafx to javafx.fxml;
    exports com.project.echarging_javafx;
}