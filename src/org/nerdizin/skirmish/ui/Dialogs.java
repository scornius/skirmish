package org.nerdizin.skirmish.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Dialogs {

    public static void showInformation(final String text) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Some Information");
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait();
    }

    public static boolean showConfirmation(final String text) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure ?");
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);

        final Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void showError(final String text) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Oops");
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait();
    }
}
