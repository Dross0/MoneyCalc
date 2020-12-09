package ru.moneycalc.gui.credit;

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
import ru.moneycalc.calculator.credit.Credit;
import ru.moneycalc.calculator.credit.CreditFactory;
import ru.moneycalc.calculator.credit.CreditInfo;
import ru.moneycalc.calculator.credit.CreditType;
import ru.moneycalc.csv.CSVWriter;
import ru.moneycalc.csv.CreditCSVFormatter;
import ru.moneycalc.gui.CalculatorController;
import ru.moneycalc.gui.FXMLLocation;
import ru.moneycalc.gui.SceneChanger;
import ru.moneycalc.utils.CalendarUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Objects;


public class CreditController implements CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CreditController.class);
    @FXML
    private TextField sumField;
    @FXML
    private TextField periodField;
    @FXML
    private ChoiceBox<String> creditTypeChoiceField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField percentField;
    @FXML
    private ChoiceBox<String> periodTypeSelection;
    @FXML
    private ChoiceBox<Character> currencySelection;
    @FXML
    private CheckBox csvCheckBox;
    @FXML
    private Label filenameLabel;
    @FXML
    private TextField filenameInput;

    private FXMLLocation fxmlLocation;
    private SceneChanger sceneChanger;

    @FXML
    private void initialize() {
        ObservableList<String> types = FXCollections.observableArrayList("аннуитетный", "дифференцированный");
        creditTypeChoiceField.setItems(types);
        creditTypeChoiceField.setValue("аннуитетный");

        ObservableList<Character> currency = FXCollections.observableArrayList('₽', '$', '€');
        currencySelection.setItems(currency);
        currencySelection.setValue('₽');

        ObservableList<String> periodTypes = FXCollections.observableArrayList("месяцев", "лет");
        periodTypeSelection.setItems(periodTypes);
        periodTypeSelection.setValue("месяцев");

        toggleShowFilenameInput();
    }

    @FXML
    public void handleCalculateButton() {
        double sumOfCredit = 0;
        double percent = 0;
        int period = 0;
        try {
            sumOfCredit = Double.parseDouble(sumField.getText());
            percent = Double.parseDouble(percentField.getText());
            period = Integer.parseInt(periodField.getText());
        } catch (NumberFormatException e) {
            logger.error("Incorrect format of numbers", e);
        }
        boolean isYearPeriod = periodTypeSelection.getValue().equals("лет");
        CreditType creditType = CreditType.ANNUITY;
        if (creditTypeChoiceField.getValue().equals("дифференцированный")) {
            creditType = CreditType.DIFFERENTIATED;
        }
        Calendar loanDate = CalendarUtils.localDateToCalendar(dateField.getValue());
        CreditInfo creditInfo = new CreditInfo(sumOfCredit, percent, period, loanDate, creditType, isYearPeriod);
        Credit credit = CreditFactory.create(creditInfo);
        if (csvCheckBox.isSelected()) {
            CreditCSVFormatter creditCSVFormatter = new CreditCSVFormatter(",");
            CSVWriter csvWriter = new CSVWriter(filenameInput.getText() + ".csv");
            csvWriter.write(creditCSVFormatter.format(credit));
        }
        showResult(fxmlLocation.getResultView(), credit);
    }

    private void showResult(URL url, Credit credit) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            AnchorPane rootPane = loader.load();
            CreditResultOutputController resultOutputController = loader.getController();
            resultOutputController.setSceneChanger(sceneChanger);
            sceneChanger.changeScene(new Scene(rootPane));
            resultOutputController.showResult(credit);
        } catch (IOException ex) {
            logger.error("Cant read result output fxml from={}", url, ex);
        }
    }

    @Override
    public void toggleShowFilenameInput() {
        filenameLabel.setVisible(!filenameLabel.isVisible());
        filenameInput.setVisible(!filenameInput.isVisible());
    }


    @Override
    public void setFXMLLocation(@NotNull FXMLLocation creditLocation) {
        this.fxmlLocation = Objects.requireNonNull(creditLocation);
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
