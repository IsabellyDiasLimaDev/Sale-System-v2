package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Views.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MenuController implements Initializable {

	@FXML
	private ImageView imgUser;
	@FXML
	private Label lblUser;

	public void clickUser() {
		User user = new User();
		user.start(imgUser);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
