module com.example.temp {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;

    opens com.example.temp to javafx.fxml;
    exports com.example.temp;
}