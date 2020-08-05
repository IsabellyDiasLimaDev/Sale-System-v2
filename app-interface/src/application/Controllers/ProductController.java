package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.repositories.impl.AppProductJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductController implements Initializable {

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
	@FXML
	private Button createButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button cancelButton;

	private boolean isCreate;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tableProduct.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		loadListProduct();
		enableEditing(false);
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

	public void buttonCreate_Action() {
		this.isCreate = true;
		this.txtfAmountPaid.setText("");
		this.txtfBarCode.setText("");
		this.txtfDescription.setText("");
		this.txtfId.setText("");
		this.txtfProfit.setText("");
		this.txtfQuantity.setText("");
		this.txtfSaleValue.setText("");
		enableEditing(true);
	}

	public void buttonUpdate_Action() {

	}

	public void createProduct() {
		AppRepository<ProductClass> repositoryProduct = new AppProductJDBC();
		ProductClass product = new ProductClass();
		try {
			if(this.isCreate == true) {
				double sale = Double.parseDouble(txtfSaleValue.getText());
				double amount = Double.parseDouble(txtfAmountPaid.getText());
				double profit = sale - amount;
				txtfProfit.setText(Double.toString(profit));
				product.setNoBarCode(Double.parseDouble(txtfBarCode.getText()));
				product.setNmDescription(txtfDescription.getText());
				product.setDtExpirationDate(expirationDate.getValue().toString());
				product.setNoAmountPaid(Double.parseDouble(txtfAmountPaid.getText()));
				product.setNoSaleValue(Double.parseDouble(txtfSaleValue.getText()));
				product.setNoProfit(profit);
				product.setNoQuantity(Integer.parseInt(txtfQuantity.getText()));
				repositoryProduct.inserir(product);
				Alert mensagem = new Alert(AlertType.INFORMATION);
				mensagem.setTitle("Concluido");
				mensagem.setHeaderText("Produto cadastrado");
				mensagem.setContentText("Produto foi cadastrado com sucesso");
				mensagem.showAndWait();
			}
		}catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao inserir ou atualizar o produto: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void buttonDelete_Action() {

	}

	public void buttonCancel_Action() {

	}
	
	private void enableEditing(Boolean edicaoEstaHabilitada) {
		this.txtfAmountPaid.setDisable(!edicaoEstaHabilitada);
		this.txtfBarCode.setDisable(!edicaoEstaHabilitada);
		this.txtfDescription.setDisable(!edicaoEstaHabilitada);
		this.expirationDate.setDisable(!edicaoEstaHabilitada);
		this.txtfQuantity.setDisable(!edicaoEstaHabilitada);
		this.txtfSaleValue.setDisable(!edicaoEstaHabilitada);
		this.saveButton.setDisable(!edicaoEstaHabilitada);
		this.cancelButton.setDisable(!edicaoEstaHabilitada);
		this.createButton.setDisable(edicaoEstaHabilitada);
		this.updateButton.setDisable(edicaoEstaHabilitada);
		this.deleteButton.setDisable(edicaoEstaHabilitada);
		this.tableProduct.setDisable(edicaoEstaHabilitada);
	}

}
