module com.example.efieldgen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.efieldgen to javafx.fxml;
    exports com.example.efieldgen;
}