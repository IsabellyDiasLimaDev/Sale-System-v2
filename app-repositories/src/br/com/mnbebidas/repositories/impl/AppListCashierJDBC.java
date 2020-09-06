package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.entities.CashierUserClass;

public class AppListCashierJDBC {

	public void userCashier(CashierUserClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblcashierlogin (cdLogin,cdCashier) VALUES (?,?);");

			comando.setInt(1, entidade.getCdLogin());
			comando.setInt(2, entidade.getCdCashier());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	

}
