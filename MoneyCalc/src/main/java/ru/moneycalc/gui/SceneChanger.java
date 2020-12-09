package ru.moneycalc.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class SceneChanger {
    private Scene menuScene;

    @NotNull
    private final Stage stage;

    public SceneChanger(@NotNull Stage stage) {
        this.stage = stage;
    }

    public void setMenuScene(@NotNull Scene menuScene) {
        this.menuScene = menuScene;
    }

    public void show() {
        stage.show();
    }

    public void changeScene(@NotNull Scene newScene) {
        stage.setScene(newScene);
    }

    public void showMenu() {
        stage.setScene(menuScene);
    }
}
