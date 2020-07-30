package application.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class User {

	public void start(ImageView imgUser) {
		Stage stage = (Stage) imgUser.getScene().getWindow();
		stage.close();
		try {
			System.out.println("Clicou!");
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("ManageUser.fxml"));
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
