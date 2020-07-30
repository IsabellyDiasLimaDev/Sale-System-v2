package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.repositories.impl.AppUserJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserController implements Initializable {

	@FXML
	private TableView<UserClass> tableUser;
	@FXML
	private Button createButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField txtfEmail;
	@FXML
	private TextField txtfUser;
	@FXML
	private TextField txtfPassword;
	@FXML
	private ComboBox<String> typeCombo;

	private Boolean isCreate;
	private UserClass userSelected;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tableUser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		loadUserTable();
		typeCombo.getItems().addAll("Administrador", "Funcionário");

	}
	
	public void buttonCreate_Action() {
		this.isCreate = true;
		this.txtfEmail.setText("");
		this.txtfUser.setText("");
		this.txtfPassword.setText("");
		enableEditing(true);
	}

	public void loadUserTable() {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			List<UserClass> users = repositoryUser.selecionar();
			ObservableList<UserClass> observableListUsers = FXCollections.observableArrayList(users);
			this.tableUser.getItems().setAll(observableListUsers);
			System.out.println(repositoryUser.selecionar());
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de usuários: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void createUser() {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			UserClass user = new UserClass();
			user.setNmEmail(txtfEmail.getText());
			user.setNmUser(txtfUser.getText());
			user.setNmPassword(txtfPassword.getText());
			user.setDsType(typeCombo.getValue());
			user.setDsStatus("Ativo");
			System.out.println(typeCombo.getValue() + "   " + typeCombo.getPromptText());
			repositoryUser.inserir(user);
			Alert mensagem = new Alert(AlertType.CONFIRMATION);
			mensagem.setTitle("Concluido");
			mensagem.setHeaderText("Usuário cadastrado");
			mensagem.setContentText("Usuário foi cadastrado com sucesso");
			mensagem.showAndWait();

		} catch (Exception e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao inserir um novo usuário: " + e.getMessage());
			mensagem.showAndWait();
		}
	}
	
	private void enableEditing(Boolean edicaoEstaHabilitada) {
		this.txtfEmail.setDisable(!edicaoEstaHabilitada);
		this.txtfUser.setDisable(!edicaoEstaHabilitada);
		this.txtfPassword.setDisable(!edicaoEstaHabilitada);
		this.typeCombo.setDisable(!edicaoEstaHabilitada);
		this.saveButton.setDisable(!edicaoEstaHabilitada);
		this.cancelButton.setDisable(!edicaoEstaHabilitada);
		this.createButton.setDisable(edicaoEstaHabilitada);
		this.updateButton.setDisable(edicaoEstaHabilitada);
		this.deleteButton.setDisable(edicaoEstaHabilitada);
		this.tableUser.setDisable(edicaoEstaHabilitada);
	}

}
