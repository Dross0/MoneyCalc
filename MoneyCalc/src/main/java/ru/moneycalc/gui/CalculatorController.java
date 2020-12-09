package ru.moneycalc.gui;

import org.jetbrains.annotations.NotNull;

public interface CalculatorController extends SceneChanging {
    void toggleShowFilenameInput();

    void setFXMLLocation(@NotNull FXMLLocation creditLocation);

    void backToMenu();
}
