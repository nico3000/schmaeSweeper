module de.schmaeddes.asciiwindow {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens de.schmaeddes.schmaesweeper to javafx.fxml;
    exports de.schmaeddes.schmaesweeper;
}