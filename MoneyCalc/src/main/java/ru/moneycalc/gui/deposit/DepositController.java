package ru.moneycalc.gui.deposit;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.moneycalc.calculator.deposit.Deposit;
import ru.moneycalc.calculator.deposit.DepositInfo;
import ru.moneycalc.calculator.deposit.FrequencyOfPayment;
import ru.moneycalc.csv.CSVWriter;
import ru.moneycalc.csv.DepositCSVFormatter;
import ru.moneycalc.gui.CalculatorController;
import ru.moneycalc.gui.FXMLLocation;
import ru.moneycalc.gui.SceneChanger;
import ru.moneycalc.utils.CalendarUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Objects;

public class DepositController implements CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(DepositController.class);

    @FXML
    private TextField sumField;
    @FXML
    private TextField periodField;
    @FXML
    private ChoiceBox<String> frequencySelection;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField percentField;
    @FXML
    private ChoiceBox<String> periodTypeSelection;
    @FXML
    private ChoiceBox<Character> currencySelection;
    @FXML
    private CheckBox capitalizationCheckBox;
    @FXML
    private CheckBox csvCheckBox;
    @FXML
    private TextField filenameInput;
    @FXML
    private Label filenameLabel;


    private FXMLLocation fxmlLocation;
    private SceneChanger sceneChanger;

    public void handleCalculateButton() {
        double sumOfDeposit = 0;
        double percent = 0;
        int period = 0;
        try {
            sumOfDeposit = Double.parseDouble(sumField.getText());
            percent = Double.parseDouble(percentField.getText());
            period = Integer.parseInt(periodField.getText());
        } catch (NumberFormatException e) {
            logger.error("Incorrect format of numbers", e);
        }
        boolean isYearPeriod = periodTypeSelection.getValue().equals("лет");
        FrequencyOfPayment frequencyOfPayment = FrequencyOfPayment.getTableOfTitles().get(frequencySelection.getValue());
        boolean isCapitalization = capitalizationCheckBox.isSelected();
        Calendar date = CalendarUtils.localDateToCalendar(dateField.getValue());
        DepositInfo depositInfo = new DepositInfo(sumOfDeposit, percent, isCapitalization, frequencyOfPayment, period, date, isYearPeriod);
        Deposit deposit = new Deposit(depositInfo);
        if (csvCheckBox.isSelected()) {
            DepositCSVFormatter depositCSVFormatter = new DepositCSVFormatter(",");
            CSVWriter csvWriter = new CSVWriter(filenameInput.getText() + ".csv");
            csvWriter.write(depositCSVFormatter.format(deposit));
        }
        showResultScene(fxmlLocation.getResultView(), deposit);

    }

    private void showResultScene(@NotNull URL resultSceneFxmlLocation, @NotNull Deposit deposit) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resultSceneFxmlLocation);
            AnchorPane rootPane = loader.load();
            DepositResultOutputController resultOutputController = loader.getController();
            sceneChanger.changeScene(new Scene(rootPane));
            resultOutputController.showResult(deposit);
            resultOutputController.setSceneChanger(sceneChanger);
        } catch (IOException ex) {
            logger.error("Cant read result output fxml from={}", resultSceneFxmlLocation, ex);
        }
    }

    @FXML
    private void initialize() {
        ObservableList<Character> currency = FXCollections.observableArrayList('₽', '$', '€');
        currencySelection.setItems(currency);
        currencySelection.setValue('₽');

        ObservableList<String> periodTypes = FXCollections.observableArrayList("месяцев", "лет");
        periodTypeSelection.setItems(periodTypes);
        periodTypeSelection.setValue("месяцев");

        ObservableList<String> frequencyTypes = FXCollections.observableArrayList(FrequencyOfPayment.getTableOfTitles().keySet());
        frequencySelection.setItems(frequencyTypes);
        frequencySelection.setValue(frequencyTypes.get(0));

        toggleShowFilenameInput();
    }

    @Override
    public void toggleShowFilenameInput() {
        filenameLabel.setVisible(!filenameLabel.isVisible());
        filenameInput.setVisible(!filenameInput.isVisible());
    }

    @Override
    public void setFXMLLocation(@NotNull FXMLLocation fxmlLocation) {
        this.fxmlLocation = Objects.requireNonNull(fxmlLocation);
    }

    @Override
    public void backToMenu() {
        sceneChanger.showMenu();
    }

    @Override
    public void setSceneChanger(@NotNull SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }
}
