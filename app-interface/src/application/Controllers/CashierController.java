package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Views.Product;
import br.com.mnbebidas.entities.CashierClass;
import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.repositories.impl.AppProductJDBC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

	// private double code;
	private double totalValueSale = 0f;
	private double totalValueProduct = 0f;
	private int quantity = 1;
	private boolean isNew = false;
	private int positionOfProduct = 0;
	private ListCashierProduct selectedProduct;
	ArrayList<ListCashierProduct> listProducts = new ArrayList<ListCashierProduct>();

	public void changeTotalValue(boolean isNew) {
		if (isNew == true) {
			totalValueSale += totalValueProduct;
			txtfTotalValueSale.setText(Double.toString(totalValueSale));
		}
	}

	public void buttonConfer_Action() {
		Product product = new Product();
		product.consult();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txtfQuantity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AppProductJDBC productRepository = new AppProductJDBC();
				String valueCode = "";
				if (!newValue.equals("")) {
					quantity = Integer.parseInt(newValue);
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
							totalValueSale -= oldTotalValue;
							totalValueSale += totalValueProduct;
							txtfTotalValueSale.setText(Double.toString(totalValueSale));
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
						txtfQuantity.setText(Integer.toString(quantity));
						txtfTotalValueProduct.setText(Double.toString(totalValueProduct));
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

}
