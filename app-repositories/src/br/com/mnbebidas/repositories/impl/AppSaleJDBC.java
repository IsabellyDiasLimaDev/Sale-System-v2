package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.SaleClass;
import br.com.mnbebidas.entities.SaleSession;
import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppSaleJDBC implements AppRepository<SaleClass> {

	@Override
	public List<SaleClass> selecionar() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserir(SaleClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblSale (cd_Login,cd_Cashier,noQuantityTotal,noTotalValue) VALUES (?,?,?,?);");
			comando.setInt(1, entidade.getCdLogin());
			comando.setInt(2, entidade.getCdCashier());
			comando.setInt(3, entidade.getNoQuantityTotal());
			comando.setDouble(4, entidade.getNoTotalValue());

			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	@Override
	public void atualizar(SaleClass entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(int id) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	public void getId() throws SQLException {
		Connection con = null;
		String sql = "SELECT MAX(cdSale) as 'cdSale' from tblSale";
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			if(rs.next()) {
				SaleSession.getInstance(rs.getInt("cdSale"));
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		
	}

}
