package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Views.MenuCashier;
import application.Views.Product;
import application.Views.TypePayment;
import br.com.mnbebidas.entities.CashierClass;
import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.SaleClass;
import br.com.mnbebidas.entities.SaleSession;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppProductJDBC;
import br.com.mnbebidas.repositories.impl.AppSaleJDBC;
import br.com.mnbebidas.repositories.impl.SaleProductJDBC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CashierController implements Initializable {

	@FXML
	private TextField txtfCode;
	@FXML
	private TextField txtfQuantity;
	@FXML
	private TextField txtfValue;
	@FXML
	private TextField txtfTotalValueProduct;
	@FXML
	private TextField txtfTotalValueSale;
	@FXML
	private TextField txtfDescription;
	@FXML
	// private ListView<ListCashierProduct> listProduct;
	private TableView<ListCashierProduct> tableProduct;
	@FXML
	private Button conferProduct;
	@FXML
	private Button updateProduct;
	@FXML
	private Button updateList;
	@FXML
	private Button newSale;
	@FXML
	private Button closeSale;
	@FXML
	private ImageView imgBack;
	@FXML
	private Label lbl;

	// private double code;
	private double totalValueSale = 0f;
	private double totalValueProduct = 0f;
	private int quantity = 1;
	private int quantityTotal = 0;
	private boolean isNew = false;
	private int positionOfProduct = 0;
	private ListCashierProduct selectedProduct;
	ArrayList<ListCashierProduct> listProducts = new ArrayList<ListCashierProduct>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txtfQuantity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AppProductJDBC productRepository = new AppProductJDBC();
				String valueCode = "";
				int oldValueQuantity = 0;
				if (!newValue.equals("")) {
					oldValueQuantity = quantity;
					quantity = Integer.parseInt(newValue);
					System.out.println("Valor atual: " + quantity + "\n Valor antigo: " + oldValueQuantity);
					valueCode = txtfCode.getText();
				}
				ListCashierProduct setProduct = new ListCashierProduct();
				try {
					ArrayList<CashierClass> products = productRepository.selectProductToCashier(valueCode);
					if (products != null && !newValue.equals("")) {
						double oldTotalValue = totalValueProduct;
						totalValueProduct = quantity * products.get(0).getNoSaleValue();
						txtfTotalValueProduct.setText(Double.toString(totalValueProduct));
						if (!isNew) {
							int positionInitial = positionOfProduct;
							quantityTotal -= oldValueQuantity;
							quantityTotal += quantity;
							System.out.println("Quantidade total " + quantityTotal);
							totalValueSale -= oldTotalValue;
							totalValueSale += totalValueProduct;
							txtfTotalValueSale.setText(Double.toString(totalValueSale));
							setProduct.setCdProduct(products.get(0).getCdProduct());
							setProduct.setPosition(positionOfProduct);
							setProduct.setNoBarCode(valueCode);
							setProduct.setNmDescription(products.get(0).getNmDescription());
							setProduct.setNoQuantity(quantity);
							setProduct.setNoValue(products.get(0).getNoSaleValue());
							setProduct.setTotalValue(totalValueProduct);
							listProducts.set(positionInitial -= 1, setProduct);
							ObservableList<ListCashierProduct> observableListProducts = FXCollections
									.observableArrayList(listProducts);
							// listProduct.setItems(observableListProducts);
							tableProduct.getItems().setAll(observableListProducts);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		});

		txtfCode.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AppProductJDBC productRepository = new AppProductJDBC();
				String valueCode = "";
				if (!newValue.equals("")) {
					valueCode = newValue;
				}
				try {
					ArrayList<CashierClass> products = productRepository.selectProductToCashier(valueCode);

					ListCashierProduct setProduct = new ListCashierProduct();
					if (products != null) {
						isNew = true;
						quantity = 1;
						totalValueProduct = quantity * products.get(0).getNoSaleValue();
						txtfDescription.setText(products.get(0).getNmDescription());
						txtfValue.setText(Double.toString(products.get(0).getNoSaleValue()));
						System.out.println(products.get(0).getCdProduct());
						txtfQuantity.setText(Integer.toString(quantity));
						txtfTotalValueProduct.setText(Double.toString(totalValueProduct));
						setProduct.setCdProduct(products.get(0).getCdProduct());
						setProduct.setPosition(positionOfProduct += 1);
						setProduct.setNoBarCode(valueCode);
						setProduct.setNmDescription(products.get(0).getNmDescription());
						setProduct.setNoQuantity(quantity);
						setProduct.setNoValue(products.get(0).getNoSaleValue());
						setProduct.setTotalValue(totalValueProduct);
						listProducts.add(setProduct);
						ObservableList<ListCashierProduct> observableListProducts = FXCollections
								.observableArrayList(listProducts);
						// listProduct.setItems(observableListProducts);
						tableProduct.getItems().setAll(observableListProducts);
						changeTotalValue(isNew);
						isNew = false;
						System.out.println(observableListProducts);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				System.out.println(" Text Changed to  " + valueCode + ")\n");
			}
		});

	}

	public void newSale() {
		txtfCode.setDisable(false);
		txtfQuantity.setDisable(false);
		tableProduct.setDisable(false);
		newSale.setDisable(true);
		closeSale.setDisable(false);
	}

	public void setsale() throws IllegalStateException, SQLException {
		if(listProducts != null) {
			//Instancia o objeto venda
			SaleClass getSale = SaleClass.getInstance(UserSession.getInstace().getCdLogin(), CashierSession.getInstace().getCdCashier(), quantityTotal, totalValueSale);
			//Adiciona no banco de dados
			AppSaleJDBC sale = new AppSaleJDBC();
			sale.inserir(getSale);
			//Chama o metodo para retornar o ultimo id de venda
			sale.getId();
			//Chama o metodo para adicionar os produtos ma tableproducts
			setProductsOnSale();
			//Abre a tela de forma de pagamento
			new TypePayment().start();
			closeSale.setDisable(true);
			//Limpa os txtf e a table
			//Inicializa a lista de produtos
		}
	}

	public void setProductsOnSale() throws IllegalStateException, SQLException {
		SaleProductJDBC sp = new SaleProductJDBC();
		// Falta alterar a quantidade de produtos quando a venda é feita
		/*
		 * Passos: buscar a quantidade no banco, armazenar em uma variavel diminuir com
		 * a quantidade total de compra atualizar a quantidade de novo no banco
		 */
		sp.insert(listProducts, listProducts.size(), SaleSession.getInstance().getCdSale());
	}

	public void changeTotalValue(boolean isNew) {
		if (isNew == true) {
			totalValueSale += totalValueProduct;
			quantityTotal += quantity;
			System.out.println("Quantidade total " + quantityTotal);
			txtfTotalValueSale.setText(Double.toString(totalValueSale));
		}
	}

	public void buttonConfer_Action() {
		Product product = new Product();
		product.consult();
	}

	public void backToMenu() {
		new MenuCashier().start(imgBack);
	}

	public void buttonUpdateList_Action() {
		this.tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
			if (newProduct != null) {
				txtfValue.setText(Double.toString(newProduct.getNoValue()));
				txtfCode.setText(newProduct.getNoBarCode());
				txtfDescription.setText(newProduct.getNmDescription());
				txtfQuantity.setText(Integer.toString(newProduct.getNoQuantity()));
				txtfTotalValueProduct.setText(Double.toString(newProduct.getTotalValue()));

				this.selectedProduct = newProduct;
			}
		});
	}

	public void buttonUpdate_Action() {

	}

}
