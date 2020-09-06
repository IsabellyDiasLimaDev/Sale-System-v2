package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Views.Cashier;
import application.Views.MenuCashier;
import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.entities.CashierUserClass;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppCashierJDBC;
import br.com.mnbebidas.repositories.impl.AppListCashierJDBC;
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

public class ListCashierController implements Initializable {
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
					AppCashierJDBC cashierjdbc = new AppCashierJDBC();
					AppListCashierJDBC userlogin = new AppListCashierJDBC();
					id = newCashier.getCdCashier();
					boolean opening = true;
					boolean closing = true;
					CashierUserClass cashiers = CashierUserClass.getInstace(UserSession.getInstace().getCdLogin(), id);
					CashierSession session = CashierSession.getInstance(id, opening, closing);
					System.out.println(id);
					System.out.println(cashiers);
					boolean isClosing = cashierjdbc.verifyStatusCashier(session);
					if (isClosing) {
						session.setClosing(false);
						userlogin.userCashier(cashiers);
						cashierjdbc.atualizar(session);
						Cashier cashier = new Cashier();
						cashier.start(lblCashier);
					} else {
						Alert mensagem = new Alert(AlertType.ERROR);
						mensagem.setTitle("Erro!");
						mensagem.setHeaderText("Caixa já aberto");
						mensagem.setContentText("Este caixa já está aberto! Selecione-o na lista de caixas abertos.");
						mensagem.showAndWait();
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

	}
}
