package application.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuCashier {
	public void start(ImageView imgCashier) {
		Stage stage = (Stage) imgCashier.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("MenuCashier.fxml"));
			Scene scene = new Scene(root, 519, 468);
			Stage newWindow = new Stage();
			newWindow.setTitle("M&N Bebidas");
			newWindow.setResizable(false);
			newWindow.setScene(scene);
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
