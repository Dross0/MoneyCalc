package ru.moneycalc.gui;

import java.net.URL;

public class FXMLLocation {
    private final URL mainView;
    private final URL resultView;

    FXMLLocation(URL mainView, URL resultView) {
        this.mainView = mainView;
        this.resultView = resultView;
    }

    public URL getMainView() {
        return mainView;
    }

    public URL getResultView() {
        return resultView;
    }

    @Override
    public String toString() {
        return "FXMLLocation{" +
                "mainView=" + mainView +
                ", resultView=" + resultView +
                '}';
    }
}
