package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.PaymentClass;
import br.com.mnbebidas.entities.SaleClass;
import br.com.mnbebidas.entities.SaleSession;
import br.com.mnbebidas.entities.TypePay;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppPaymentJDBC;
import br.com.mnbebidas.repositories.impl.AppSaleJDBC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TypePaymentController implements Initializable {
	@FXML
	private ComboBox<String> typePayCombo;
	@FXML
	private TextField txtfDebit;
	@FXML
	private TextField txtfCredit;
	@FXML
	private TextField txtfCash;
	@FXML
	private ImageView imgBack;
	@FXML
	private Button setPay;
	@FXML
	private Button cancelPay;
	@FXML
	private Label lblDebit;
	@FXML
	private Label lblCredit;
	@FXML
	private Label lblCash;

	private String payment = "";

	public void backToCashier() {
		Stage stage = (Stage) lblCash.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		disableEditing(true);
		typePayCombo.getItems().addAll("Cart�o de Cr�dito", "Cart�o de D�bito", "Dinheiro", "Cart�o de D�bito/Cr�dito",
				"Cart�o de D�bito/Dinheiro", "Cart�o de Cr�dito/Dinheiro");
		typePayCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				payment = newValue;
				setPay.setDisable(false);
				cancelPay.setDisable(false);
				if (newValue.equals("Cart�o de Cr�dito")) {
					txtfCredit.setDisable(false);
					lblCredit.setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(1, newValue);
				} else if (newValue.equals("Cart�o de D�bito")) {
					txtfDebit.setDisable(false);
					lblDebit.setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(2, newValue);
				} else if (newValue.equals("Dinheiro")) {
					txtfCash.setDisable(false);
					lblCash.setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(3, newValue);
				} else if (newValue.equals("Cart�o de D�bito/Cr�dito")) {
					txtfDebit.setDisable(false);
					lblDebit.setDisable(false);
					txtfCredit.setDisable(false);
					lblCredit.setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(4, newValue);
				} else if (newValue.equals("Cart�o de D�bito/Dinheiro")) {
					txtfDebit.setDisable(false);
					lblDebit.setDisable(false);
					txtfCash.setDisable(false);
					lblCash  .setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(5, newValue);
				} else if (newValue.equals("Cart�o de Cr�dito/Dinheiro")) {
					txtfCredit.setDisable(false);
					lblCredit.setDisable(false);
					txtfCash.setDisable(false);
					lblCash.setDisable(false);
					typePayCombo.setDisable(true);
					TypePay.getInstance(6, newValue);
				}
			}
		});
	}

	private void disableEditing(boolean enable) {
		txtfCash.setDisable(enable);
		txtfCredit.setDisable(enable);
		txtfDebit.setDisable(enable);
		lblCash.setDisable(enable);
		lblCredit.setDisable(enable);
		lblDebit.setDisable(enable);
		setPay.setDisable(enable);
		cancelPay.setDisable(enable);

	}
	
public void cancelpayment() {
	txtfCash.setText("");
	txtfCredit.setText("");
	txtfDebit.setText("");
	typePayCombo.setDisable(false);
	disableEditing(true);
}

	public void setPayment() throws SQLException, IllegalStateException, IOException {
		AppSaleJDBC newSaleJDBC = new AppSaleJDBC();
		newSaleJDBC.getId(UserSession.getInstace().getCdLogin());
		if (payment.equals("Cart�o de D�bito")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfDebit.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
		}
		if (payment.equals("Cart�o de Cr�dito")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfCredit.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
		}
		if (payment.equals("Dinheiro")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfCash.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
		}
		if (payment.equals("Cart�o de D�bito/Cr�dito")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfDebit.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
			double parcialValue2 = Double.parseDouble(txtfCredit.getText());
			PaymentClass pay2 = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue2);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay2);
		}
		if (payment.equals("Cart�o de D�bito/Dinheiro")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfDebit.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
			double parcialValue2 = Double.parseDouble(txtfCash.getText());
			PaymentClass pay2 = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue2);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay2);
		}
		if (payment.equals("Cart�o de Cr�dito/Dinheiro")) {
			int cdTypePay = TypePay.getInstance().getCdTypePay();
			int cdSale = SaleSession.getInstance().getCdSale();
			double parcialValue = Double.parseDouble(txtfCredit.getText());
			PaymentClass pay = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay);
			double parcialValue2 = Double.parseDouble(txtfCash.getText());
			PaymentClass pay2 = PaymentClass.getInstance(cdTypePay, cdSale, parcialValue2);
			System.out.println(SaleClass.getInstance());
			new AppPaymentJDBC().inserir(pay2);
		}
		
		backToCashier();
	}

}
