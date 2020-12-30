package br.com.mnbebidas.repositories.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mnbebidas.connection.ConnectionJDBC;
import br.com.mnbebidas.entities.CashierClass;
import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppProductJDBC implements AppRepository<ProductClass> {

	@Override
	public List<ProductClass> selecionar() throws SQLException, IOException {
		Connection con = null;
		List<ProductClass> products = new ArrayList<ProductClass>();
		String sql = "select * from tblproduct";
		try {
			con = ConnectionJDBC.createConnection();
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			while (rs.next()) {
				ProductClass product = new ProductClass();
				SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
				Date datefor = null;
				datefor = rs.getDate("dtExpirationDate");
				formatador.format(datefor);
				product.setCdProduct(rs.getInt("cdProduct"));
				product.setNoBarCode(rs.getString("noBarCode"));
				product.setNmDescription(rs.getString("nmDescription"));
				product.setDtExpirationDate(formatador.format(datefor));
				product.setNoAmountPaid(rs.getDouble("noAmountPaid"));
				product.setNoSaleValue(rs.getDouble("noSaleValue"));
				product.setNoProfit(rs.getDouble("noProfit"));
				product.setNoQuantity(rs.getInt("noQuantity"));
				products.add(product);
				System.out.println(product.getDtExpirationDate());
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return products;
	}

	@Override
	public void inserir(ProductClass entidade) throws SQLException, IOException {
		Connection con = null;
		try {
			con = ConnectionJDBC.createConnection();
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblProduct (noBarCode,nmDescription,dtExpirationDate,noAmountPaid,noSaleValue,noProfit,noQuantity) VALUES (?,?,?,?,?,?,?);");
			// SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
			// String data = entidade.getDtExpirationDate();
			// Date datefor = null;
			// datefor = formater.parse(data);
			// formater.applyPattern("yyyy-MM-dd");
			comando.setString(1, entidade.getNoBarCode());
			comando.setString(2, entidade.getNmDescription());
			comando.setString(3, entidade.getDtExpirationDate());
			comando.setDouble(4, entidade.getNoAmountPaid());
			comando.setDouble(5, entidade.getNoSaleValue());
			comando.setDouble(6, entidade.getNoProfit());
			comando.setInt(7, entidade.getNoQuantity());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	@Override
	public void atualizar(ProductClass entidade) throws SQLException, IOException {
		Connection con = null;
		try {
			con = ConnectionJDBC.createConnection();
			PreparedStatement comando = con.prepareStatement(
					"UPDATE tblproduct SET noBarCode = ?, nmDescription = ?, dtExpirationDate = ?, noAmountPaid = ?, noSaleValue = ?,noProfit = ?, noQuantity = ? WHERE cdProduct = ?;");
			comando.setString(1, entidade.getNoBarCode());
			comando.setString(2, entidade.getNmDescription());
			comando.setString(3, entidade.getDtExpirationDate());
			comando.setDouble(4, entidade.getNoAmountPaid());
			comando.setDouble(5, entidade.getNoSaleValue());
			comando.setDouble(6, entidade.getNoProfit());
			comando.setInt(7, entidade.getNoQuantity());
			comando.setInt(8, entidade.getCdProduct());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	@Override
	public void excluir(int id) throws SQLException, IOException {
		Connection con = null;
		boolean isNext = false;
		try {
			con = ConnectionJDBC.createConnection();
			PreparedStatement comando = con.prepareStatement("DELETE FROM tblProduct WHERE cdProduct = ?");
			comando.setInt(1, id);
			comando.execute();
			System.out.println(isNext);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public ArrayList<CashierClass> selectProductToCashier(String noBarCode) throws SQLException, IOException {
		Connection con = null;
		boolean isNext = false;
		ArrayList<CashierClass> product = new ArrayList<CashierClass>();
		try {
			con = ConnectionJDBC.createConnection();
			String query = "SELECT cdProduct,nmDescription, noSaleValue, noQuantity FROM tblProduct WHERE noBarCode = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, noBarCode);
			ResultSet rs = ps.executeQuery();
			isNext = rs.next();
			if (isNext) {
				CashierClass products = CashierClass.getInstance(rs.getInt("cdProduct"),rs.getString("nmDescription"), rs.getDouble("noSaleValue"), rs.getInt("noQuantity"));

				product.add(products);
				System.out.println(products.getNmDescription());
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return product;
	}

	
	public void updateQuantityProduct(ArrayList<ListCashierProduct> products, int qtd) throws SQLException, IOException {
		Connection con = null;
		try {
			con = ConnectionJDBC.createConnection();
			for(int i = 0; i< qtd; i++) {
				PreparedStatement comando = con.prepareStatement(
						"UPDATE tblproduct SET noQuantity = ? WHERE cdProduct = ?;");
				int newQuantity = products.get(i).getNoQuantityProduct() - products.get(i).getNoQuantity();
				comando.setInt(1, newQuantity);
				comando.setInt(2, products.get(i).getCdProduct());
				comando.execute();
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
