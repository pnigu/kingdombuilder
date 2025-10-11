module com.example.kingdombuilder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kingdombuilder to javafx.fxml;
    exports com.example.kingdombuilder;
}