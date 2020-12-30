package br.com.mnbebidas.repositories.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AppRepository<T> {
	List<T> selecionar() throws SQLException, IOException;

	void inserir(T entidade) throws SQLException, IOException;

	void atualizar(T entidade) throws SQLException, IOException;

	void excluir(int id) throws SQLException, IOException;
}
