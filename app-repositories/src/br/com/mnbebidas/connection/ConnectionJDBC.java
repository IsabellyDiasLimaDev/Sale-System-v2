package br.com.mnbebidas.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionJDBC {

	public static Connection createConnection() throws IOException, SQLException {
		InputStream is = ConnectionJDBC.class.getClassLoader().getResourceAsStream("application.properties");
		if (is == null) {
			throw new FileNotFoundException("O arquivo de configuração do banco de dados não foi encontrado.");
		}
		Properties props = new Properties();
		props.load(is);
		Connection conexao = DriverManager.getConnection(props.getProperty("jdbc:mysql://localhost:3306/dbpdv?useTimezone=true&serverTimezone=UTC"),
				props.getProperty("root"), props.getProperty("Dias042012"));
		return conexao;
	}

}
