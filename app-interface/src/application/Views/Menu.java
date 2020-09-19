package application.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {
	public void createMenuAdmin(Label lbl) {
		Stage stage = (Stage) lbl.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
			Scene scene = new Scene(root, 600, 472);
			Stage newWindow = new Stage();
			newWindow.setTitle("M&N Bebidas");
			newWindow.setResizable(false);
			newWindow.setScene(scene);
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createMenu(Label lbl) {
		Stage stage = (Stage) lbl.getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("/application/Views/MenuFunc.fxml"));
			Scene scene = new Scene(root, 600, 472);
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
