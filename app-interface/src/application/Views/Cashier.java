package application.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cashier {

	public void start(ImageView imgLock) {
		Stage stage = (Stage) imgLock.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("Cashier.fxml"));
			Scene scene = new Scene(root, 600, 582);
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
