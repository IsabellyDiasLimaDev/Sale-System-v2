package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	private TableView<ListCashierProduct> tableProduct;

	// private double code;
	private double totalValueSale = 0f;
	private double totalValueProduct = 0f;
	private int quantity = 1;
	private boolean isNew = false;
	private int positionOfProduct = 0;
	ArrayList<ListCashierProduct> listProducts = new ArrayList<ListCashierProduct>();

	public void changeTotalValue(boolean isNew) {
		if (isNew == true) {
			totalValueSale += totalValueProduct;
			txtfTotalValueSale.setText(Double.toString(totalValueSale));
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtfQuantity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AppProductJDBC productRepository = new AppProductJDBC();
				quantity = Integer.parseInt(newValue);
				long valueCode = Long.parseLong(txtfCode.getText());
				try {
					ArrayList<CashierClass> products = productRepository.selectProductToCashier(valueCode);
					if (products != null) {
						double oldTotalValue = totalValueProduct;
						totalValueProduct = quantity * products.get(0).getNoSaleValue();
						txtfTotalValueProduct.setText(Double.toString(totalValueProduct));
						if (!isNew) {
							totalValueSale -= oldTotalValue;
							totalValueSale += totalValueProduct;
							txtfTotalValueSale.setText(Double.toString(totalValueSale));
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
				long valueCode = Long.parseLong(newValue);
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
						ObservableList<ListCashierProduct> observableListProducts = FXCollections.observableArrayList(listProducts);
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
