package ru.moneycalc.gui.credit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.credit.Credit;
import ru.moneycalc.calculator.credit.CreditPayment;
import ru.moneycalc.gui.SceneChanger;
import ru.moneycalc.gui.SceneChanging;

public class CreditResultOutputController implements SceneChanging {
    @FXML
    private TableView<CreditPaymentRepresentation> tableOfCredit;
    @FXML
    private TableColumn<CreditPaymentRepresentation, Integer> numberColumn;
    @FXML
    private TableColumn<CreditPaymentRepresentation, String> dateColumn;
    @FXML
    private TableColumn<CreditPaymentRepresentation, Double> sumColumn;
    @FXML
    private TableColumn<CreditPaymentRepresentation, Double> principalColumn;
    @FXML
    private TableColumn<CreditPaymentRepresentation, Double> percentColumn;
    @FXML
    private TableColumn<CreditPaymentRepresentation, Double> balanceColumn;
    @FXML
    private Label totalSumLabel;
    @FXML
    private Label totalPercentsLabel;
    private SceneChanger sceneChanger;


    public void showResult(@NotNull Credit credit) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("paymentSum"));
        principalColumn.setCellValueFactory(new PropertyValueFactory<>("principalPart"));
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("percentPart"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("creditBalance"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableOfCredit.setItems(getPayments(credit));
        CreditPayment totalPayment = new CreditPayment(credit.getTotalPayout(), 0, credit.getTotalPercentPayout(), 0);
        CreditPaymentRepresentation paymentRepresentation = new CreditPaymentRepresentation(totalPayment); // convert result to String
        totalSumLabel.setText(paymentRepresentation.getPaymentSum());
        totalPercentsLabel.setText(paymentRepresentation.getPercentPart());
    }

    public void backToMenu() {
        sceneChanger.showMenu();
    }

    private ObservableList<CreditPaymentRepresentation> getPayments(Credit credit) {
        ObservableList<CreditPaymentRepresentation> creditPayments = FXCollections.observableArrayList();
        int monthsNum = credit.getInfo().getMonths();
        for (int monthIndex = 1; monthIndex <= monthsNum; monthIndex++) {
            creditPayments.add(new CreditPaymentRepresentation(credit.getPaymentByMonthNumber(monthIndex), monthIndex));
        }
        return creditPayments;
    }

    @Override
    public void setSceneChanger(SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }
}
