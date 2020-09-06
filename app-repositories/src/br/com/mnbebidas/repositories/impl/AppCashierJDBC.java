package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.CashierSession;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppCashierJDBC implements AppRepository<CashierSession> {
	
	
	public List<CashierSession> listCashiersOfStatus(boolean isClosing) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CashierSession> cashiers = new ArrayList<CashierSession>();
		String sql = "select cdCashier,opening,closing from tblcashier where closing = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, isClosing);
			rs = ps.executeQuery();
			while (rs.next()) {
				CashierSession cashier = CashierSession.getInstance(rs.getInt("cdCashier"),rs.getBoolean("opening"),rs.getBoolean("closing"));
				cashiers.add(cashier);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return cashiers;
	}

	public boolean verifyStatusCashier(CashierSession entidade) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		boolean isClosing = false;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			String query = "SELECT * FROM tblcashier WHERE cdcashier = ? and closing = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, entidade.getCdCashier());
			ps.setBoolean(2, entidade.isClosing());

			resultSet = ps.executeQuery();
			isClosing = resultSet.next();

		} finally {
			if (con != null) {
				con.close();
			}
		}

		return isClosing;
	}

	@Override
	public List<CashierSession> selecionar() throws SQLException {
		Connection con = null;
		List<CashierSession> cashiers = new ArrayList<CashierSession>();
		String sql = "select cdCashier,opening,closing from tblcashier";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			while (rs.next()) {
				CashierSession cashier = CashierSession.getInstance(rs.getInt("cdCashier"),rs.getBoolean("opening"),rs.getBoolean("closing"));
				cashiers.add(cashier);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return cashiers;
	}

	@Override
	public void inserir(CashierSession entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(CashierSession entidade) throws SQLException {
		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement("UPDATE tblcashier SET closing = ? WHERE cdCashier = ?");

			comando.setBoolean(1, entidade.isClosing());
			comando.setInt(2, entidade.getCdCashier());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}
	
	public boolean verifyDate(String date, int cdLogin) throws SQLException {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		boolean isDate = false;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			String sql = "SELECT * FROM tblcashierlogin WHERE DATE(created_at) = ? AND cdLogin = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, date);
			ps.setInt(2, cdLogin);

			resultSet = ps.executeQuery();
			isDate = resultSet.next();
		}finally{
			if (con != null) {
				con.close();
			}
		}
		
		return isDate;
	}

	@Override
	public void excluir(int id) throws SQLException {
		// TODO Auto-generated method stub

	}

}
