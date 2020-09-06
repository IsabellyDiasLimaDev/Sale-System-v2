package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.mnbebidas.entities.ListCashierClass;
import br.com.mnbebidas.entities.ListCashierProduct;
import br.com.mnbebidas.entities.ProductClass;

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

}
