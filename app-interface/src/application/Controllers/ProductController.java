package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.repositories.impl.AppProductJDBC;
import br.com.mnbebidas.repositories.impl.AppUserJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private ProductClass selectedProduct;
	double sale = 0;
	double amount = 0;
	double profit = 0;
	List<ProductClass> products = new ArrayList<ProductClass>();

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
			products = productRepository.selecionar();
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
		this.expirationDate.setValue(null);
		this.txtfBarCode.setText("");
		this.txtfDescription.setText("");
		this.txtfId.setText("");
		this.txtfProfit.setText("");
		this.txtfQuantity.setText("");
		this.txtfSaleValue.setText("");
		enableEditing(true);
	}

	public void buttonUpdate_Action() {
		this.isCreate = false;
		enableEditing(true);
	}

	public void createProduct() {
		AppRepository<ProductClass> repositoryProduct = new AppProductJDBC();
		ProductClass product = new ProductClass();
		try {
			if (this.isCreate == true) {
				product.setNoBarCode(txtfBarCode.getText());
				product.setNmDescription(txtfDescription.getText());
				product.setDtExpirationDate(expirationDate.getValue().toString());
				product.setNoAmountPaid(Double.parseDouble(txtfAmountPaid.getText()));
				product.setNoSaleValue(Double.parseDouble(txtfSaleValue.getText()));
				product.setNoProfit(profit);
				product.setNoQuantity(Integer.parseInt(txtfQuantity.getText()));
				repositoryProduct.inserir(product);
				products.add(product);
				loadListProduct();
				Alert mensagem = new Alert(AlertType.INFORMATION);
				mensagem.setTitle("Concluido");
				mensagem.setHeaderText("Produto cadastrado");
				mensagem.setContentText("Produto foi cadastrado com sucesso");
				mensagem.showAndWait();
			} else {
				double sale = Double.parseDouble(txtfSaleValue.getText());
				double amount = Double.parseDouble(txtfAmountPaid.getText());
				double profit = sale - amount;
				txtfProfit.setText(Double.toString(profit));
				product.setNoBarCode(txtfBarCode.getText());
				product.setNmDescription(txtfDescription.getText());
				product.setDtExpirationDate(expirationDate.getValue().toString());
				product.setNoAmountPaid(Double.parseDouble(txtfAmountPaid.getText()));
				product.setNoSaleValue(Double.parseDouble(txtfSaleValue.getText()));
				product.setNoProfit(profit);
				product.setNoQuantity(Integer.parseInt(txtfQuantity.getText()));
				product.setCdProduct(Integer.parseInt(txtfId.getText()));
				repositoryProduct.atualizar(product);
				loadListProduct();
				Alert mensagem = new Alert(AlertType.INFORMATION);
				mensagem.setTitle("Concluido");
				mensagem.setHeaderText("Produto atualizado");
				mensagem.setContentText("Produto foi atualizado com sucesso");
				mensagem.showAndWait();
			}
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao inserir ou atualizar o produto: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void buttonDelete_Action() throws SQLException {
		this.isCreate = false;
		enableEditing(true);
		deleteProduct();
	}

	public void deleteProduct() throws SQLException {
		AppRepository<ProductClass> repositoryProduct = new AppProductJDBC();
		int id = Integer.parseInt(txtfId.getText());
		Alert mensagem = new Alert(AlertType.WARNING);
		mensagem.setTitle("Atenção!");
		mensagem.setHeaderText("Exclusão de produto");
		mensagem.setContentText("Tem certeza que deseja excluir este produto?");
		Optional<ButtonType> result = mensagem.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			repositoryProduct.excluir(id);
			loadListProduct();
			this.txtfAmountPaid.setText("");
			this.expirationDate.setValue(null);
			this.txtfBarCode.setText("");
			this.txtfDescription.setText("");
			this.txtfId.setText("");
			this.txtfProfit.setText("");
			this.txtfQuantity.setText("");
			this.txtfSaleValue.setText("");
		}
	}

	public void buttonCancel_Action() {
		enableEditing(false);
		this.tableProduct.getSelectionModel().selectFirst();
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
