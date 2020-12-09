package ru.moneycalc.gui.deposit;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.deposit.Deposit;
import ru.moneycalc.calculator.deposit.DepositPayment;
import ru.moneycalc.gui.SceneChanger;
import ru.moneycalc.gui.SceneChanging;

import java.util.Calendar;

public class DepositResultOutputController implements SceneChanging {
    @FXML
    private TableView<DepositPaymentRepresentation> tableOfDeposit;
    @FXML
    private TableColumn<DepositPaymentRepresentation, Integer> numberColumn;
    @FXML
    private TableColumn<DepositPaymentRepresentation, String> dateColumn;
    @FXML
    private TableColumn<DepositPaymentRepresentation, Double> interestChargesColumn;
    @FXML
    private TableColumn<DepositPaymentRepresentation, Double> taxesColumn;
    @FXML
    private TableColumn<DepositPaymentRepresentation, Double> sumIncludingTaxColumn;
    @FXML
    private TableColumn<DepositPaymentRepresentation, Double> depositBalanceColumn;
    @FXML
    private Label totalSumLabel;
    @FXML
    private Label totalInterestChargesLabel;
    @FXML
    private Label totalTaxesLabel;
    @FXML
    private Label totalSumWithTaxesLabel;
    private SceneChanger sceneChanger;


    public void showResult(@NotNull Deposit deposit) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        interestChargesColumn.setCellValueFactory(new PropertyValueFactory<>("interestCharges"));
        taxesColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfTaxes"));
        sumIncludingTaxColumn.setCellValueFactory(new PropertyValueFactory<>("sumIncludingTax"));
        depositBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("depositBalance"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableOfDeposit.setItems(getPayments(deposit));
        DepositPayment totalPayment = new DepositPayment(deposit.getTotalSum(), deposit.getAllInterestCharges(), deposit.getAllTaxes());
        DepositPaymentRepresentation paymentRepresentation = new DepositPaymentRepresentation(totalPayment);
        totalSumLabel.setText(paymentRepresentation.getDepositBalance());
        totalInterestChargesLabel.setText(paymentRepresentation.getInterestCharges());
        totalSumWithTaxesLabel.setText(paymentRepresentation.getSumIncludingTax());
        totalTaxesLabel.setText(paymentRepresentation.getAmountOfTaxes());
    }


    public void backToMenu() {
        sceneChanger.showMenu();
    }

    private ObservableList<DepositPaymentRepresentation> getPayments(@NotNull Deposit deposit) {
        ObservableList<DepositPaymentRepresentation> depositPayments = FXCollections.observableArrayList();
        int months = deposit.getInitDepositInfo().getMonths();
        Calendar depositDate = deposit.getInitDepositInfo().getDepositDate();
        int paymentsNum = deposit.getInitDepositInfo().getFrequency().getNumberOfPayouts(months, depositDate);
        for (int paymentIndex = 0; paymentIndex < paymentsNum; paymentIndex++) {
            depositPayments.add(new DepositPaymentRepresentation(deposit.getPaymentByIndex(paymentIndex), paymentIndex + 1));
        }
        return depositPayments;
    }


    @Override
    public void setSceneChanger(SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }
}
