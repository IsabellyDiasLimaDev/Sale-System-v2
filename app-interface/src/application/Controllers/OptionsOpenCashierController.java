package application.Controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import application.Views.Cashier;
import application.Views.ListCashiersOfStatus;
import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.entities.UserSession;
import br.com.mnbebidas.repositories.impl.AppCashierJDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class OptionsOpenCashierController {
	@FXML
	private ImageView imgBack;
	@FXML
	private Label lbl;

	public void closeCashier() throws SQLException {
		// atualizar a instancia do caixa para caixa fechado
		CashierSession cashier = CashierSession.getInstace();
		cashier.setClosing(true);
		// Chamar o JDBC responsavel por atualizar o status do caixa
		AppCashierJDBC cashierJDBC = new AppCashierJDBC();
		// Passar a instancia do caixa para o metodo que atualiza o status do caixa
		try {
			cashierJDBC.atualizar(cashier);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Abrir novamente o menu de caixas fechados abertos
		new ListCashiersOfStatus().start(imgBack);

	}
	
	public void returnSale(){
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			boolean isActive = new AppCashierJDBC().verifyDate(date, UserSession.getInstace().getCdLogin());
			if(isActive) {
				new Cashier().start(lbl);
			}
			else {
				Alert mensagem = new Alert(AlertType.ERROR);
				mensagem.setTitle("Erro!");
				mensagem.setHeaderText("Sessão entre caixa e usuário");
				mensagem.setContentText("Esta sessão não correponde a este dia, for favor feche o caixa e abra uma nova sessão!");
				mensagem.showAndWait();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
