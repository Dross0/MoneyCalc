package ru.moneycalc;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.moneycalc.gui.Controller;
import ru.moneycalc.gui.SceneChanger;

import java.io.IOException;

public class MainApp extends Application {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
    public static final String MENU_FXML = "mainMenu.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource(MENU_FXML));
            SplitPane root = loader.load();
            Controller controller = loader.getController();
            SceneChanger sceneChanger = new SceneChanger(stage);
            sceneChanger.setMenuScene(new Scene(root));
            controller.setSceneChanger(sceneChanger);
            sceneChanger.showMenu();
            sceneChanger.show();
        } catch (IOException ex) {
            logger.error("Cant read menu fxml from={}", MENU_FXML, ex);
        }
    }
}
