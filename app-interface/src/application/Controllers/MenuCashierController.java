package application.Controllers;

import application.Views.Cashier;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MenuCashierController {

	@FXML
	private ImageView imgUnlock;
	
	public void openCashier() {
		Cashier cashier = new Cashier();
		cashier.start(imgUnlock);
	}
	
}
