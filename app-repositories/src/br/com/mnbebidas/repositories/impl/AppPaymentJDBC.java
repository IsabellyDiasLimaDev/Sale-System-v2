package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.mnbebidas.entities.PaymentClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppPaymentJDBC implements AppRepository<PaymentClass> {

	@Override
	public List<PaymentClass> selecionar() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserir(PaymentClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con
					.prepareStatement("INSERT INTO tblPayment (cd_TypePay,cd_Sale,noParcialValue) VALUES (?,?,?);");
			comando.setInt(1, entidade.getCdTypePay());
			comando.setInt(2, entidade.getCdSale());
			comando.setDouble(3, entidade.getParcialValue());

			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	@Override
	public void atualizar(PaymentClass entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(int id) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getId(String nmPayment) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		int cdTypePay = 0;

		String sql = "SELECT cdTypePay FROM tblTypePay WHERE nmTypePay = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setString(1, nmPayment);
			resultSet = ps.executeQuery();
			if(resultSet.next()) {
				cdTypePay = resultSet.getInt("cdTypePay");	
			}
			
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return cdTypePay;
	}
}
