package ru.moneycalc.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.moneycalc.gui.credit.CreditController;
import ru.moneycalc.gui.credit.CreditResultOutputController;
import ru.moneycalc.gui.deposit.DepositController;
import ru.moneycalc.gui.deposit.DepositResultOutputController;

import java.io.IOException;

public class Controller implements SceneChanging {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private SceneChanger sceneChanger;


    public void startCreditCalculator() {
        FXMLLocation creditLocation = new FXMLLocation(CreditController.class.getClassLoader().getResource("creditView.fxml"),
                CreditResultOutputController.class.getClassLoader().getResource("creditResultOutput.fxml"));
        startController(creditLocation);
    }

    public void startDepositCalculator() {
        FXMLLocation depositLocation = new FXMLLocation(DepositController.class.getClassLoader().getResource("depositView.fxml"),
                DepositResultOutputController.class.getClassLoader().getResource("depositResultOutput.fxml"));
        startController(depositLocation);

    }

    public void startController(@NotNull FXMLLocation fxmlLocation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlLocation.getMainView());
            AnchorPane rootPane = loader.load();
            CalculatorController calculatorController = loader.getController();
            calculatorController.setFXMLLocation(fxmlLocation);
            calculatorController.setSceneChanger(sceneChanger);
            Scene scene = new Scene(rootPane);
            sceneChanger.changeScene(scene);
        } catch (IOException ex) {
            logger.error("Cant load fxml from fxml location={}", fxmlLocation, ex);
        }
    }

    @Override
    public void setSceneChanger(@NotNull SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }
}
