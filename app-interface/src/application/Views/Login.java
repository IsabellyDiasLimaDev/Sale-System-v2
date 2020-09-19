package application.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root, 600, 420);
			// primaryStage.getIcons().add(new Image("wine.png"));
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("M&N Bebidas");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(Label lblLogout) {
		Stage stage = (Stage) lblLogout.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root, 600, 420);
			Stage newWindow = new Stage();
			newWindow.setTitle("M&N Bebidas");
			newWindow.setResizable(false);
			newWindow.setScene(scene);
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
