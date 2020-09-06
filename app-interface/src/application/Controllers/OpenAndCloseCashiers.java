package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Views.MenuCashier;
import application.Views.OptionsOpenCashier;
import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.repositories.impl.AppCashierJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class OpenAndCloseCashiers implements Initializable{
	@FXML
	ListView<CashierSession> listOpenCashiers;
	@FXML
	ListView<CashierSession> listClosedCashiers;
	
	boolean isClosing = false;
	
	private int id;
	
	@FXML
	private Label lbl;
	
	@FXML
	private ImageView imgBack;
	
	List<CashierSession> list = new ArrayList<CashierSession>();
	List<CashierSession> list2 = new ArrayList<CashierSession>();

	public void loadLists() {
		AppCashierJDBC cashierRepository = new AppCashierJDBC();
		try {
			list = cashierRepository.listCashiersOfStatus(isClosing);
			ObservableList<CashierSession> observableListCashier = FXCollections.observableArrayList(list);
			listOpenCashiers.setItems(observableListCashier);
			isClosing = true;
			list2 = cashierRepository.listCashiersOfStatus(isClosing);
			ObservableList<CashierSession> observableListCashier2 = FXCollections.observableArrayList(list2);
			listClosedCashiers.setItems(observableListCashier2);
			
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de Caixas: " + e.getMessage());
			mensagem.showAndWait();
		}

	}
	
	public void backToMenu() {
		new MenuCashier().start(imgBack);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadLists();
this.listOpenCashiers.getSelectionModel().selectedItemProperty().addListener((obs,oldCashier,newCashier)->{
		if(newCashier != null) {
			id = newCashier.getCdCashier();
			boolean opening = true;
			boolean closing = false;
			CashierSession.getInstance(id, opening, closing);
			OptionsOpenCashier ops = new OptionsOpenCashier();
			ops.start(lbl);
		}
	
});	
	}
}
