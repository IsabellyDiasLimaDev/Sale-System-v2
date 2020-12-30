package br.com.mnbebidas.repositories.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.connection.ConnectionJDBC;
import br.com.mnbebidas.entities.SaleClass;
import br.com.mnbebidas.entities.SaleSession;
import br.com.mnbebidas.entities.SaleViewClass;
import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppSaleJDBC implements AppRepository<SaleClass> {

	@Override
	public List<SaleClass> selecionar() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SaleViewClass> select() throws SQLException, IOException {
		Connection con = null;
		List<SaleViewClass> sales = new ArrayList<SaleViewClass>();
		String sql = "SELECT tblSale.cdSale,\r\n" + "tblSale.noQuantityTotal,\r\n" + "tblSale.noTotalValue,\r\n"
				+ "tbltypepay.nmTypePay,\r\n" + "tblLogin.nmUser,\r\n" + "tblCashier.cdCashier\r\n" + "FROM (((\r\n"
				+ "tblSale\r\n" + "INNER JOIN tblTypePay ON tblSale.cd_TypePay = tbltypepay.cdTypePay)\r\n"
				+ "INNER JOIN tblLogin ON tblSale.cd_Login = tblLogin.cdLogin)\r\n"
				+ "INNER JOIN tblCashier ON  tblsale.cd_Cashier = tblcashier.cdCashier);";
		try {
			con = ConnectionJDBC.createConnection();
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			while (rs.next()) {
				SaleViewClass sale = SaleViewClass.getInstance(rs.getInt("cdSale"), rs.getInt("noQuantityTotal"),
						rs.getDouble("noTotalValue"), rs.getString("nmTypePay"), rs.getString("nmUser"),
						rs.getInt("cdCashier"));
				sales.add(sale);
				System.out.print(sale);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return sales;
	}

	@Override
	public void inserir(SaleClass entidade) throws SQLException, IOException {
		Connection con = null;
		try {
			con = ConnectionJDBC.createConnection();
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblSale (cd_Login,cd_Cashier,cd_TypePay,noQuantityTotal,noTotalValue) VALUES (?,?,?,?,?);");
			comando.setInt(1, entidade.getCdLogin());
			comando.setInt(2, entidade.getCdCashier());
			comando.setInt(3, entidade.getCdTypePay());
			comando.setInt(4, entidade.getNoQuantityTotal());
			comando.setDouble(5, entidade.getNoTotalValue());

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

	public void getId(int cdLogin) throws SQLException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT MAX(cdSale) as 'cdSale' from tblSale WHERE cd_Login = ?";
		try {
			con = ConnectionJDBC.createConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cdLogin);
			rs = ps.executeQuery();
			if (rs.next()) {
				SaleSession.getInstance(rs.getInt("cdSale"));
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

}
