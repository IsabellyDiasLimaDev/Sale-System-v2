package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.ListCashierClass;
import br.com.mnbebidas.repositories.impl.AppCashierJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;

public class ListCashierController implements Initializable{
	@FXML
	private ListView<ListCashierClass> listCashiers;
	
	private int id;
	
	List<ListCashierClass> list = new ArrayList<ListCashierClass>();
	
	public void loadListCashiers() {
		AppRepository<ListCashierClass> cashierRepository = new AppCashierJDBC();
		try {
			list = cashierRepository.selecionar();
			ObservableList<ListCashierClass> observableListCashier = FXCollections.observableArrayList(list);
			listCashiers.setItems(observableListCashier);
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de Caixas: " + e.getMessage());
			mensagem.showAndWait();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.listCashiers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		loadListCashiers();
		this.listCashiers.getSelectionModel().selectedItemProperty().addListener((obs,oldCashier,newCashier)->{
			if(newCashier != null) {
				id = newCashier.getCdCashier();
				System.out.println(id);
			}
		});
		
	}
}
