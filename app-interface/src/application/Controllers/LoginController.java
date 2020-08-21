package application.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Views.Menu;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppLoginJDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField txtfUser;

	@FXML
	private PasswordField txtfPassword;

	@FXML
	private Button loginButton;

	public void loginAction() throws SQLException, IOException {

		String user = txtfUser.getText().toString();
		String password = txtfPassword.getText().toString();
		ArrayList<UserSession> userSession = new ArrayList<UserSession>();
		try {
			AppLoginJDBC login = new AppLoginJDBC();
			Boolean verifyUser = login.verifyUser(user, password);
			Boolean verifyAdmin = login.verifyAdmin(user, password);
			if (!verifyUser) {
				Alert mensagem = new Alert(AlertType.ERROR);
				mensagem.setTitle("Erro!");
				mensagem.setHeaderText("Usuário ou senha incorretos");
				mensagem.setContentText("Verifique seu usuário e senha e tente novamente!");
				mensagem.showAndWait();
			} else {
				login.selectUser(user, password);
				
				if (verifyAdmin) {
					Menu menu = new Menu();
					menu.createMenuAdmin(loginButton);
				} else {
					Menu menu = new Menu();
					menu.createMenu(loginButton);
				}
			}
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro na verificação do usuário: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

}
