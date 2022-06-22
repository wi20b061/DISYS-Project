module com.project.echarging_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.project.echarging_javafx to javafx.fxml;
    exports com.project.echarging_javafx;
}