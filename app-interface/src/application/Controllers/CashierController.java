package application.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.mnbebidas.repositories.impl.AppPaymentJDBC;
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
import javafx.scene.control.ComboBox;
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
	private ComboBox<String> typePayCombo;
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
	private Button updateButton;
	@FXML
	private Button removeButton;
	@FXML
	private Button generateCupomButton;
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
	private boolean isUpdate = false;
	private int positionOfProduct = 0;
	private ListCashierProduct selectedProduct;

	ArrayList<ListCashierProduct> listProducts = new ArrayList<ListCashierProduct>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		typePayCombo.getItems().addAll("Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Cartão de Débito/Crédito",
				"Cartão de Débito/Dinheiro", "Cartão de Crédito/Dinheiro");

		this.tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
			if (newProduct != null) {
				this.selectedProduct = newProduct;
			}
		});

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
						if (!isNew && !isUpdate) {
							int positionInitial = positionOfProduct;
							quantityTotal -= oldValueQuantity;
							quantityTotal += quantity;
							totalValueSale -= oldTotalValue;
							totalValueSale += totalValueProduct;
							txtfTotalValueSale.setText(Double.toString(totalValueSale));
							setProduct.setCdProduct(products.get(0).getCdProduct());
							setProduct.setPosition(positionOfProduct);
							setProduct.setNoBarCode(valueCode);
							setProduct.setNmDescription(products.get(0).getNmDescription());
							setProduct.setNoQuantity(quantity);
							setProduct.setNoValue(products.get(0).getNoSaleValue());
							setProduct.setNoQuantityProduct(products.get(0).getNoQuantity());
							setProduct.setTotalValue(totalValueProduct);
							listProducts.set(positionInitial -= 1, setProduct);
							ObservableList<ListCashierProduct> observableListProducts = FXCollections
									.observableArrayList(listProducts);
							// listProduct.setItems(observableListProducts);
							tableProduct.getItems().setAll(observableListProducts);
						}
						if (isUpdate) {
							int position = selectedProduct.getPosition() - 1;
							quantityTotal -= oldValueQuantity;
							quantityTotal += quantity;
							totalValueSale -= oldTotalValue;
							totalValueSale += totalValueProduct;
							txtfTotalValueSale.setText(Double.toString(totalValueSale));
							setProduct.setCdProduct(products.get(0).getCdProduct());
							setProduct.setPosition(positionOfProduct);
							setProduct.setNoBarCode(valueCode);
							setProduct.setNmDescription(products.get(0).getNmDescription());
							setProduct.setNoQuantity(quantity);
							setProduct.setNoValue(products.get(0).getNoSaleValue());
							setProduct.setNoQuantityProduct(products.get(0).getNoQuantity());
							setProduct.setTotalValue(totalValueProduct);
							listProducts.set(position, setProduct);
							ObservableList<ListCashierProduct> observableListProducts = FXCollections
									.observableArrayList(listProducts);
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
					if (products != null && !isUpdate) {
						isNew = true;
						quantity = 1;
						totalValueProduct = quantity * products.get(0).getNoSaleValue();
						txtfDescription.setText(products.get(0).getNmDescription());
						txtfValue.setText(Double.toString(products.get(0).getNoSaleValue()));
						System.out.println(products.get(0).getCdProduct());
						txtfQuantity.setText(Integer.toString(quantity));
						txtfTotalValueProduct.setText(Double.toString(totalValueProduct));
						setProduct.setNoQuantityProduct(products.get(0).getNoQuantity());
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
		typePayCombo.setDisable(false);
		txtfQuantity.setDisable(false);
		tableProduct.setDisable(false);
		newSale.setDisable(true);
		closeSale.setDisable(false);
		generateCupomButton.setDisable(false);
		clearAllItems();
	}

	public void setsale() throws IllegalStateException, SQLException {
		if (listProducts != null) {
			int id = new AppPaymentJDBC().getId(typePayCombo.getValue());
			System.out.println("Id do tipo da venda: " + id);
			// Instancia o objeto venda
			SaleClass getSale = SaleClass.getInstance(UserSession.getInstace().getCdLogin(),
					CashierSession.getInstace().getCdCashier(), id, quantityTotal, totalValueSale);
			// Adiciona no banco de dados
			AppSaleJDBC sale = new AppSaleJDBC();
			sale.inserir(getSale);
			// Chama o metodo para retornar o ultimo id de venda
			sale.getId(UserSession.getInstace().getCdLogin());
			// Chama o metodo para adicionar os produtos ma tableproducts
			setProductsOnSale();
			// Abre a tela de forma de pagamento
			// new TypePayment().start();
			desabilityTxtfAndTable();

		}
	}

	public void setProductsOnSale() throws IllegalStateException, SQLException {
		SaleProductJDBC sp = new SaleProductJDBC();
		AppProductJDBC p = new AppProductJDBC();
		sp.insert(listProducts, listProducts.size(), SaleSession.getInstance().getCdSale());
		p.updateQuantityProduct(listProducts, listProducts.size());
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

	public void buttonRemove_Action() {
		this.tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
			if (newProduct != null) {
				this.selectedProduct = newProduct;
			}
		});
		if (this.selectedProduct != null) {
			int position = this.selectedProduct.getPosition() - 1;
			listProducts.remove(position);
			positionOfProduct = 0;
			for (int i = 0; i < listProducts.size(); i++) {
				listProducts.get(i).setPosition(positionOfProduct += 1);
			}
			ObservableList<ListCashierProduct> observableListProducts = FXCollections.observableArrayList(listProducts);
			tableProduct.getItems().setAll(observableListProducts);
		}
	}

	public void backToMenu() {
		new MenuCashier().start(imgBack);
	}

	public void updateList() {
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

	public void buttonUpdateProduct_Action() {
		String privileges = UserSession.getInstace().getPrivileges();
		if (privileges.equals("Administrator")) {
			new Product().consult();
		} else {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Somente administrador");
			mensagem.setContentText("Acesso somente para os administradores do sistema!");
			mensagem.showAndWait();

		}
	}

	public void buttonUpdate_Action() {
		txtfCode.setDisable(true);
		isUpdate = true;
		updateList();
	}

	public void clearAllItems() throws IndexOutOfBoundsException {
		File cupom = new File("cupom.txt");
		cupom.delete();
		txtfCode.setText("");
		typePayCombo.setValue("");
		txtfDescription.setText("");
		txtfQuantity.setText("");
		txtfTotalValueProduct.setText("");
		txtfTotalValueSale.setText("");
		txtfValue.setText("");
		tableProduct.getItems().clear();
		positionOfProduct = 0;
		totalValueSale = 0f;
		totalValueProduct = 0f;
		quantity = 1;
		quantityTotal = 0;
		isNew = false;
		try {
			listProducts.clear();
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	public void desabilityTxtfAndTable() {
		txtfCode.setDisable(true);
		typePayCombo.setDisable(true);
		txtfQuantity.setDisable(true);
		tableProduct.setDisable(true);
		newSale.setDisable(false);
		closeSale.setDisable(true);
		generateCupomButton.setDisable(true);
	}

	public void generateCupom() {
		try {
			File cupon = new File("cupom.txt");
			if (cupon.createNewFile()) {
				System.out.println("Cupom Gerado!");
				FileWriter myWriter = new FileWriter("cupom.txt");
				myWriter.write("M&N Bebidas\n");
				String date = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
				myWriter.write("-------------------------\n");
				myWriter.write(date + "\n");
				myWriter.write("-------------------------\n");
				myWriter.write("Item  Código/Descrição                                      QTDE  Venda  Total\n");
				for (int i = 0; i < listProducts.size(); i++) {
					myWriter.write(listProducts.get(i).getPosition() + " " + listProducts.get(i).getNoBarCode() + " "
							+ listProducts.get(i).getNmDescription() + " " + listProducts.get(i).getNoQuantityProduct()
							+ " " + listProducts.get(i).getNoValue() + " " + listProducts.get(i).getTotalValue()
							+ "\n");
				}
				myWriter.write("Total R$                                                                 "
						+ totalValueSale + "\n");
				myWriter.write("Caixa: " + CashierSession.getInstace().getCdCashier() + "\n");
				myWriter.close();
				System.out.println(cupon.getAbsolutePath());
				System.out.println("Successfully wrote to the file.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}
}
