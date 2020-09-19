package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.repositories.impl.AppProductJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConferProductController implements Initializable {
	@FXML
	private TableView<ProductClass> tableProduct;
	@FXML
	private TextField txtfId;
	@FXML
	private TextField txtfBarCode;
	@FXML
	private TextField txtfDescription;
	@FXML
	private DatePicker expirationDate;
	@FXML
	private TextField txtfAmountPaid;
	@FXML
	private TextField txtfSaleValue;
	@FXML
	private TextField txtfProfit;
	@FXML
	private TextField txtfQuantity;

	private boolean isCreate;
	private ProductClass selectedProduct;
	double sale = 0;
	double amount = 0;
	double profit = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tableProduct.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		loadListProduct();
		enableEditing(false);

		this.tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
			if (newProduct != null) {
				txtfAmountPaid.setText(Double.toString(newProduct.getNoAmountPaid()));
				txtfBarCode.setText(newProduct.getNoBarCode());
				txtfDescription.setText(newProduct.getNmDescription());
				txtfId.setText(Integer.toString(newProduct.getCdProduct()));
				txtfProfit.setText(Double.toString(newProduct.getNoProfit()));
				txtfQuantity.setText(Integer.toString(newProduct.getNoQuantity()));
				txtfSaleValue.setText(Double.toString(newProduct.getNoSaleValue()));
				expirationDate.setValue(LOCAL_DATE(newProduct.getDtExpirationDate()));

				this.selectedProduct = newProduct;
			}
		});

		txtfSaleValue.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// 7891095600847
				sale = Double.parseDouble(txtfSaleValue.getText());
				amount = Double.parseDouble(txtfAmountPaid.getText());
				profit = sale - amount;
				txtfProfit.setText(Double.toString(profit));

			}
		});

	}

	public static final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	public void loadListProduct() {
		try {
			AppRepository<ProductClass> productRepository = new AppProductJDBC();
			List<ProductClass> products = productRepository.selecionar();
			ObservableList<ProductClass> observableListProducts = FXCollections.observableArrayList(products);
			this.tableProduct.getItems().setAll(observableListProducts);

		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de usuários: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	private void enableEditing(Boolean edicaoEstaHabilitada) {
		this.txtfAmountPaid.setDisable(!edicaoEstaHabilitada);
		this.txtfBarCode.setDisable(!edicaoEstaHabilitada);
		this.txtfDescription.setDisable(!edicaoEstaHabilitada);
		this.expirationDate.setDisable(!edicaoEstaHabilitada);
		this.txtfQuantity.setDisable(!edicaoEstaHabilitada);
		this.txtfSaleValue.setDisable(!edicaoEstaHabilitada);
		this.tableProduct.setDisable(edicaoEstaHabilitada);
	}
}
