package br.com.mnbebidas.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.mnbebidas.entities.UserClass;
import br.com.mnbebidas.repositories.interfaces.AppRepository;

public class AppUserJDBC implements AppRepository<UserClass> {

	@Override
	public List<UserClass> selecionar() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		List<UserClass> users = new ArrayList<UserClass>();
		String sql = "select * from tbllogin";
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				UserClass user = new UserClass();
				user.setCdLogin(rs.getInt("cdLogin"));
				user.setNmEmail(rs.getString("nmEmail"));
				user.setNmUser(rs.getString("nmUser"));
				user.setNmPassword(rs.getString("nmPassword"));
				user.setDsType(rs.getString("dsType"));
				user.setDsStatus(rs.getString("dsStatus"));
				users.add(user);
				System.out.print(users);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return users;
	}

	@Override
	public void inserir(UserClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"INSERT INTO tblLogin (nmEmail,nmUser,nmPassword,dsType,dsStatus) VALUES (?,?,?,?,?);");
			comando.setString(1, entidade.getNmEmail());
			comando.setString(2, entidade.getNmUser());
			comando.setString(3, entidade.getNmPassword());
			comando.setString(4, entidade.getDsType());
			comando.setString(5, entidade.getDsStatus());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	@Override
	public void atualizar(UserClass entidade) throws SQLException {

		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"UPDATE tblLogin SET nmEmail = ?, nmUser = ?, nmPassword= ?, dsType= ?,dsStatus= ? WHERE cdLogin = ?;");
			comando.setString(1, entidade.getNmEmail());
			comando.setString(2, entidade.getNmUser());
			comando.setString(3, entidade.getNmPassword());
			comando.setString(4, entidade.getDsType());
			comando.setString(5, entidade.getDsStatus());
			comando.setInt(6, entidade.getCdLogin());
			comando.execute();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	@Override
	public void excluir(UserClass entidade) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC",
					"root", "Dias042012");
			PreparedStatement comando = con.prepareStatement(
					"DELETE FROM tblLogin WHERE cdLogin = ?");
			comando.setInt(1, entidade.getCdLogin());
		}finally {
			if (con != null) {
				con.close();
			}
		}
	}

}
