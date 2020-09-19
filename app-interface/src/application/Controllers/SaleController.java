package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.SaleProductViewClass;
import br.com.mnbebidas.entities.SaleViewClass;
import br.com.mnbebidas.repositories.impl.AppSaleJDBC;
import br.com.mnbebidas.repositories.impl.SaleProductJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;

public class SaleController implements Initializable {

	@FXML
	private TableView<SaleViewClass> tableSale;
	@FXML
	private TableView<SaleProductViewClass> tableProduct;
	
	private SaleViewClass selectedProduct;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadSaleTable();
		this.tableSale.getSelectionModel().selectedItemProperty().addListener((obs,oldProduct,newProduct)->{
			if(newProduct != null) {
				this.selectedProduct = newProduct;
				loadProductsSale();
				System.out.println(this.selectedProduct.getCdSale());
			}
		});
	}
	
	public void loadSaleTable() {
		try {
			AppSaleJDBC repositorySale = new AppSaleJDBC();
			List<SaleViewClass> sales = repositorySale.select();
			ObservableList<SaleViewClass> observableListSales = FXCollections.observableArrayList(sales);
			this.tableSale.getItems().setAll(observableListSales);
			System.out.println(sales);
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de usuários: " + e.getMessage());
			mensagem.showAndWait();
		}
	}
	
	public void loadProductsSale() {
		try {
			SaleProductJDBC saleRepository = new SaleProductJDBC();
			if(this.selectedProduct != null) {
				List<SaleProductViewClass> products = saleRepository.select(this.selectedProduct.getCdSale());
				ObservableList<SaleProductViewClass> observableListSales = FXCollections.observableArrayList(products);
				this.tableProduct.getItems().setAll(observableListSales);
				System.out.println(products);
			}
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de usuários: " + e.getMessage());
			mensagem.showAndWait();
		}
	}
	
}
