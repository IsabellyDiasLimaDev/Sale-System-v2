package br.com.mnbebidas.repositories.impl;

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

import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppProductJDBC implements AppRepository<ProductClass> {

	@Override
	public List<ProductClass> selecionar() throws SQLException {
		Connection con = null;
		List<ProductClass> products = new ArrayList<ProductClass>();
		String sql = "select * from tblproduct";
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			Statement comando = con.createStatement();
			ResultSet rs = comando.executeQuery(sql);
			while (rs.next()) {
				ProductClass product = new ProductClass();
				SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
				Date datefor = null;
				datefor = rs.getDate("dtExpirationDate");
				formatador.format(datefor);
				product.setCdProduct(rs.getInt("cdProduct"));
				product.setNoBarCode(rs.getDouble("noBarCode"));
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
	public void inserir(ProductClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblProduct (noBarCode,nmDescription,dtExpirationDate,noAmountPaid,noSaleValue,noProfit,noQuantity) VALUES (?,?,?,?,?,?,?);");
			// SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
			// String data = entidade.getDtExpirationDate();
			// Date datefor = null;
			// datefor = formater.parse(data);
			// formater.applyPattern("yyyy-MM-dd");
			comando.setDouble(1, entidade.getNoBarCode());
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
	public void atualizar(ProductClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"UPDATE tblproduct SET noBarCode = ?, nmDescription = ?, dtExpirationDate = ?, noAmountPaid = ?, noSaleValue = ?,noProfit = ?, noQuantity = ? WHERE cdProduct = ?;");
			comando.setDouble(1, entidade.getNoBarCode());
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
	public void excluir(ProductClass entidade) throws SQLException {
		Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
                    "root", "Dias042012");
            PreparedStatement comando = con.prepareStatement(
                    "DELETE FROM tblProduct WHERE cdProduct = ?");
            comando.setInt(1, entidade.getCdProduct());
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
