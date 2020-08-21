package application.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
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
	private TextField txtfId;
	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private ComboBox<String> statusCombo;

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
				txtfId.setText(Integer.toString(newUser.getCdLogin()));
				this.seletedUser = newUser;
			}
		});
		loadUserTable();
		typeCombo.getItems().addAll("Administrador", "Funcionário");
		statusCombo.getItems().addAll("Ativo", "Inativo");
		statusCombo.setDisable(true);

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

	public void buttonDelete_Action() {
		this.isCreate = false;
		enableEditing(true);
		deleteUser();
	}

	public void deleteUser() {
		try {
			AppRepository<UserClass> repositoryUser = new AppUserJDBC();
			int id = Integer.parseInt(txtfId.getText());
			Alert mensagem = new Alert(AlertType.WARNING);
			mensagem.setTitle("Atenção!");
			mensagem.setHeaderText("Exclusão de usuário");
			mensagem.setContentText("Tem certeza que deseja excluir este usuário?");
			Optional<ButtonType> result = mensagem.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				repositoryUser.excluir(id);
			}

		} catch (SQLException e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao tentar excluir um usuï¿½rio: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void loadUserTable() {
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
			mensagem.setContentText("Houve um erro ao obter a lista de usuários: " + e.getMessage());
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
				mensagem.setHeaderText("Usuï¿½rio cadastrado");
				mensagem.setContentText("Usuï¿½rio foi cadastrado com sucesso");
				mensagem.showAndWait();
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
				mensagem.setHeaderText("Usuï¿½rio atualizado");
				mensagem.setContentText("Usuï¿½rio foi atualizado com sucesso");
				mensagem.showAndWait();
			}

		} catch (Exception e) {
			Alert mensagem = new Alert(AlertType.ERROR);
			mensagem.setTitle("Erro!");
			mensagem.setHeaderText("Erro no banco de dados");
			mensagem.setContentText("Houve um erro ao inserir ou atualizar o usuï¿½rio: " + e.getMessage());
			mensagem.showAndWait();
		}
	}

	public void buttonCancel_Action() {
		enableEditing(false);
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
