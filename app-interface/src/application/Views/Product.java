package application.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Product {
	public void start(ImageView imgProduct) {
		Stage stage = (Stage) imgProduct.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("ManageProduct.fxml"));
			Scene scene = new Scene(root, 775, 622);
			Stage newWindow = new Stage();
			newWindow.setTitle("M&N Bebidas");
			newWindow.setResizable(false);
			newWindow.setScene(scene);
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void consult() {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("ConferProduct.fxml"));
			Scene scene = new Scene(root, 775, 622);
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
