package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.ListCashierClass;
import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.ProductClass;
import br.com.mnbebidas.entities.SaleProductViewClass;

public class SaleProductJDBC {

	public void insert(ArrayList<ListCashierProduct> products,int qtd, int cdSale) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			for (int i = 0; i < qtd; i++) {
				PreparedStatement comando = con.prepareStatement(
						"INSERT INTO tblsaleproduct (cdSale,cdProduct,noValue,noQuantity) VALUES (?,?,?,?);");
				comando.setInt(1, cdSale);
				comando.setInt(2, products.get(i).getCdProduct());
				System.out.println(products.get(i).getCdProduct());
				comando.setDouble(3, products.get(i).getNoValue());
				comando.setInt(4, products.get(i).getNoQuantity());
				comando.execute();
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
	public List<SaleProductViewClass> select(int cdSale) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		List<SaleProductViewClass> products = new ArrayList<SaleProductViewClass>();
		
		String sql = "SELECT tblProduct.noBarCode,\r\n" + 
				"tblProduct.nmDescription,\r\n" + 
				"tblSaleProduct.noValue,\r\n" + 
				"tblSaleProduct.noQuantity\r\n" + 
				"FROM (( tblSaleProduct\r\n" + 
				"INNER JOIN tblProduct ON tblSaleProduct.cdProduct = tblProduct.cdProduct)\r\n" + 
				"INNER JOIN tblSale ON tblSaleProduct.cdSale = tblSale.cdSale) WHERE tblSale.cdSale = ?;";
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setInt(1, cdSale);
			resultSet = ps.executeQuery();
			while(resultSet.next()) {
				SaleProductViewClass product = SaleProductViewClass.getInstance(resultSet.getString("noBarCode"), resultSet.getString("nmDescription"), resultSet.getDouble("noValue"),resultSet.getInt("noQuantity"));
				products.add(product);	
			}
			
		} finally {
			if (con != null) {
				con.close();
			}
		}
		
		return products;
	}

}
