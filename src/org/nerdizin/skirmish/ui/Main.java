package org.nerdizin.skirmish.ui;

import org.nerdizin.skirmish.game.Game;
import org.nerdizin.skirmish.util.ExceptionCatchingEventDispatcherWrapper;
import org.nerdizin.skirmish.util.Translator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        Translator.init(Locale.ENGLISH);

        final Game game = new Game();
        game.initControlPanel(525, 0);
        game.init();

        final Scene scene = new Scene(game.getGroup(), 700, 500);
        scene.getStylesheets().add("resources/css/style.css");

        // catch all exceptions
        primaryStage.setEventDispatcher(new ExceptionCatchingEventDispatcherWrapper(primaryStage.getEventDispatcher()));

        primaryStage.setTitle("Skirmish");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}