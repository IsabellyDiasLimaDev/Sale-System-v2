package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Views.Login;
import application.Views.MenuCashier;
import application.Views.Product;
import application.Views.User;
import br.com.mnbebidas.entities.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MenuController implements Initializable {

	@FXML
	private ImageView imgUser;
	@FXML
	private Label lblUser;
	@FXML
	private ImageView imgProduct;
	@FXML
	private ImageView imgCashier;
	@FXML
	private Label lblProduct;
	@FXML
	private Label lblUsername;
	@FXML
	private Label lblType;
	@FXML
	private Label lblLogout;
	@FXML
	private ImageView imgLogout;

	// private ArrayList<UserSession> session = new ArrayList<UserSession>();

	public void clickUser() {
		User user = new User();
		user.start(imgUser);
	}

	public void clickProduct() {
		Product product = new Product();
		product.start(imgProduct);
	}

	public void clickCashier() {
		MenuCashier cashier = new MenuCashier();
		cashier.start(imgCashier);
	}
	
	public void clickLogout() {
		Login login = new Login();
		login.logout(lblLogout);
	}

	public void isNotAdmin() {
		Alert mensagem = new Alert(AlertType.ERROR);
		mensagem.setTitle("Erro!");
		mensagem.setHeaderText("Somente administrador");
		mensagem.setContentText("Acesso somente para os administradores do sistema!");
		mensagem.showAndWait();
	}

	public void setSession() {
		lblUsername.setText(UserSession.getInstace().getUserName());
		// System.out.println("Session: " + session.get(0).getUserName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 setSession();

	}

}
