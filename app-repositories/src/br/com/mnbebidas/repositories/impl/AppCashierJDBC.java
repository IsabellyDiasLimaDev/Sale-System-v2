package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.CashierUserClass;
import br.com.mnbebidas.entities.ListCashierClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppCashierJDBC implements AppRepository<ListCashierClass>{

	@Override
	public List<ListCashierClass> selecionar() throws SQLException {
		Connection con = null;
		List<ListCashierClass> cashiers = new ArrayList<ListCashierClass>();
		String sql = "select * from tblcashier";
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			while(rs.next()) {
				ListCashierClass cashier = new ListCashierClass();
				cashier.setCdCashier(rs.getInt("cdCashier"));
				cashiers.add(cashier);
			}
		}finally {
			if (con != null) {
				con.close();
			}
		}
		return cashiers;
	}

	@Override
	public void inserir(ListCashierClass entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(ListCashierClass entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(int id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public void openCashier(CashierUserClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblcashieruser (cdLogin,cdCashier,opening,closing) VALUES (?,?,?,?);");
			
			comando.setInt(1, entidade.getCdLogin());
			comando.setInt(2, entidade.getCdCashier());
			comando.setBoolean(3, entidade.isOpening());
			comando.setBoolean(4, entidade.isClosing());
		}finally {
			
		}
	}
	
	

}
