package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Views.Product;
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
	@FXML
	private ImageView imgProduct;
	@FXML
	private Label lblProduct;

	public void clickUser() {
		User user = new User();
		user.start(imgUser);
	}
	public void clickProduct() {
		Product user = new Product();
		user.start(imgProduct);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
