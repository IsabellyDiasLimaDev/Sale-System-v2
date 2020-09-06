package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Views.Cashier;
import application.Views.ListCashiers;
import application.Views.ListCashiersOfStatus;
import application.Views.Menu;
import application.Views.MenuCashier;
import br.com.mnbebidas.entities.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MenuCashierController implements Initializable {

	@FXML
	private ImageView imgUnlock;
	@FXML
	private Label lblUsername;
	@FXML
	private ImageView imgList;
	
	public void openCashier() {
		ListCashiers cashier = new ListCashiers();
		cashier.start(imgList);
	}
	
	public void setSession() {
		lblUsername.setText(UserSession.getInstace().getUserName());
		// System.out.println("Session: " + session.get(0).getUserName());
	}
	
	public void openList() {
		ListCashiersOfStatus cashier = new ListCashiersOfStatus();
		cashier.start(imgList);
	}
	
	public void backToMenu() {
		String privilegies = UserSession.getInstace().getPrivileges();
		if(privilegies.equals("Administrador")) {
			new Menu().createMenuAdmin(lblUsername);
		}
		else {
			new Menu().createMenu(lblUsername);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setSession();
		
	}
	
	
	
}
