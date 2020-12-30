package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Views.Menu;
import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppUserJDBC;
import br.com.mnbebidas.repositories.interfaces.AppRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
	private TextField txtfId;
	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private ComboBox<String> statusCombo;
	@FXML
	private Label lbl;

	private Boolean isCreate;
	private UserClass userSelected;
	private UserClass seletedUser;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tableUser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		enableEditing(false);
		txtfId.setDisable(true);
		this.tableUser.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
			if (newUser != null) {
				txtfEmail.setText(newUser.getNmEmail());
				txtfUser.setText(newUser.getNmUser());
				txtfPassword.setText(newUser.getNmPassword());
				typeCombo.setValue(newUser.getDsType());
				statusCombo.setValue(newUser.getDsStatus());
				txtfId.setText(Integer.toString(newUser.getCdLogin()));
				this.seletedUser = newUser;
			}
		});
		try {
			loadUserTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeCombo.getItems().addAll("Administrador", "Funcion�rio");
		statusCombo.getItems().addAll("Ativo", "Inativo");
		statusCombo.setDisable(true);

	}

	public void backToMenu() {
		String privilegies = UserSession.getInstace().getPrivileges();
		if (privilegies.equals("Administrador")) {
			new Menu().createMenuAdmin(lbl);
		} else {
			new Menu().createMenu(lbl);
		}
	}

	public void buttonCreate_Action() {
		this.isCreate = true;
		this.txtfEmail.setText("");
		this.txtfUser.setText("");
		this.txtfPassword.setText("");
		enableEditing(true);
	}

	public void buttonUpdate_Action() {
		this.isCreate = false;
		enableEditing(true);
		statusCombo.setDisable(false);
	}

	public void buttonDelete_Action() throws IOException {
		this.isCreate = false;
		enableEditing(true);
		deleteUser();
	}

	public void deleteUser() throws IOException {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			int id = Integer.parseInt(txtfId.getText());
			Alert mensagem = new Alert(AlertType.WARNING);
			mensagem.setTitle("Aten��o!");
			mensagem.setHeaderText("Exclus�o de usu�rio");
			mensagem.setContentText("Tem certeza que deseja excluir este usu�rio?");
			Optional<ButtonType> result = mensagem.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				repositoryUser.excluir(id);
				loadUserTable();
			}

		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao tentar excluir um usu�rio: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void loadUserTable() throws IOException {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			List<UserClass> users = repositoryUser.selecionar();
			ObservableList<UserClass> observableListUsers = FXCollections.observableArrayList(users);
			this.tableUser.getItems().setAll(observableListUsers);
			System.out.println(users);
		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao obter a lista de usu�rios: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void createUser() {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			UserClass user = new UserClass();
			if (this.isCreate == true) {
				user.setNmEmail(txtfEmail.getText());
				user.setNmUser(txtfUser.getText());
				user.setNmPassword(txtfPassword.getText());
				user.setDsType(typeCombo.getValue());
				user.setDsStatus("Ativo");
				repositoryUser.inserir(user);
				Alert mensagem = new Alert(AlertType.INFORMATION);
				mensagem.setTitle("Concluido");
				mensagem.setHeaderText("Usu�rio cadastrado");
				mensagem.setContentText("Usu�rio foi cadastrado com sucesso");
				mensagem.showAndWait();
				loadUserTable();
			} else {
				user.setNmEmail(txtfEmail.getText());
				user.setNmUser(txtfUser.getText());
				user.setNmPassword(txtfPassword.getText());
				user.setDsType(typeCombo.getValue());
				user.setDsStatus(statusCombo.getValue());
				user.setCdLogin(Integer.parseInt(txtfId.getText()));
				repositoryUser.atualizar(user);
				Alert mensagem = new Alert(AlertType.INFORMATION);
				mensagem.setTitle("Concluido");
				mensagem.setHeaderText("Usu�rio atualizado");
				mensagem.setContentText("Usu�rio foi atualizado com sucesso");
				mensagem.showAndWait();
				loadUserTable();
			}

		} catch (Exception e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao inserir ou atualizar o usu�rio: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void buttonCancel_Action() {
		enableEditing(false);
		statusCombo.setDisable(true);
		this.tableUser.getSelectionModel().selectFirst();
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
