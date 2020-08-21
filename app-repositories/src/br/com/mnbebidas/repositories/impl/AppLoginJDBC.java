package br.com.mnbebidas.repositories.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.entities.UserSession;

public class AppLoginJDBC {

	public boolean verifyUser(String user, String password) throws SQLException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		Boolean isUser = false;

		String sql = "SELECT * FROM tblLogin WHERE nmUser = ? and nmPassword = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			resultSet = ps.executeQuery();
			isUser = resultSet.next();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return isUser;
	}

	public boolean verifyAdmin(String user, String password) throws SQLException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		Boolean isAdmin = false;

		String sql = "SELECT * FROM tblLogin WHERE nmUser = ? and nmPassword = ? and dsType = ?";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			ps.setString(3, "Administrador");
			resultSet = ps.executeQuery();
			isAdmin = resultSet.next();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return isAdmin;
	}

	public void selectUser(String user, String password) throws SQLException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		String sql = "SELECT cdLogin, nmUser, dsType FROM tblLogin WHERE nmUser = ? and nmPassword = ?";
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				int cd = resultSet.getInt("cdLogin");
				String userName = resultSet.getString("nmUser");
				String privilegies = resultSet.getString("dsType");
				// Obtém o objeto pelo `UserSession.getInstace`
				UserSession userSession = UserSession.getInstace(cd, userName, privilegies);
				System.out.print(userSession);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

}
