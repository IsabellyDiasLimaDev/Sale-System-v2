package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Views.Menu;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppLoginJDBC;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController implements Initializable {

	@FXML
	private TextField txtfUser;

	@FXML
	private PasswordField txtfPassword;

	@FXML
	private Button loginButton;
	
	@FXML
	private Label lbl;

	
	public void loginAction() throws SQLException, IOException {

		String user = txtfUser.getText().toString();
		String password = txtfPassword.getText().toString();
		ArrayList<UserSession> userSession = new ArrayList<UserSession>();
		try {
			AppLoginJDBC login = new AppLoginJDBC();
			Boolean verifyUser = login.verifyUser(user, password);
			Boolean verifyAdmin = login.verifyAdmin(user, password);
			Boolean verifyActive = login.isAtivo(user);
			if (!verifyUser) {
				Alert mensagem = new Alert(AlertType.ERROR);
				mensagem.setTitle("Erro!");
				mensagem.setHeaderText("Usuário ou senha incorretos");
				mensagem.setContentText("Verifique seu usuário e senha e tente novamente!");
				mensagem.showAndWait();
			} 
			else if(!verifyActive) {
				Alert mensagem = new Alert(AlertType.ERROR);
				mensagem.setTitle("Erro!");
				mensagem.setHeaderText("Usuário Inativo");
				mensagem.setContentText("Este usuário está inativo!");
				mensagem.showAndWait();
			}
			else {
				login.selectUser(user, password);
				
				if (verifyAdmin) {
					Menu menu = new Menu();
					menu.createMenuAdmin(lbl);
				} else {
					Menu menu = new Menu();
					menu.createMenu(lbl);
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


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtfPassword.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                try {
						loginAction();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		
	}

}
