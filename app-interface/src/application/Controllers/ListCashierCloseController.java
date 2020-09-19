package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Views.MenuCashier;
import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.repositories.impl.AppCashierJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ListCashierCloseController implements Initializable {
	@FXML
	private ListView<CashierSession> listCashiers;
	@FXML
	private Label lblCashier;
	@FXML
	private ImageView imgBack;

	private int id;

	List<CashierSession> list = new ArrayList<CashierSession>();

	public void loadListCashiers() {
		AppRepository<CashierSession> cashierRepository = new AppCashierJDBC();
		try {
			list = cashierRepository.selecionar();
			ObservableList<CashierSession> observableListCashier = FXCollections.observableArrayList(list);
			listCashiers.setItems(observableListCashier);
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
		this.listCashiers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		loadListCashiers();
		this.listCashiers.getSelectionModel().selectedItemProperty().addListener((obs, oldCashier, newCashier) -> {
			try {
				if (newCashier != null) {
					AppCashierJDBC cashier = new AppCashierJDBC();
					id = newCashier.getCdCashier();
					boolean opening = false;
					boolean closing = true;
					CashierSession session = CashierSession.getInstance(id, opening, closing);
					cashier.atualizar(session);
					Alert mensagem = new Alert(AlertType.INFORMATION);
					mensagem.setTitle("Caixa Fechado");
					mensagem.setHeaderText("Sucesso");
					mensagem.setContentText("Caixa Fechado com sucesso!");
					mensagem.showAndWait();
					Stage stage = (Stage) lblCashier.getScene().getWindow();
					stage.close();
				}
			} catch (SQLException e) {
				Alert mensagem = new Alert(AlertType.ERROR);
				mensagem.setTitle("Erro!");
				mensagem.setHeaderText("Erro ao fechar o caixa");
				mensagem.setContentText("Houve um erro ao fechar o caixa!" + e.getMessage());
				mensagem.showAndWait();
			}
		});

	}
}
