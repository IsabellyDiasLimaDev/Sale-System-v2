package br.com.mnbebidas.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface AppRepository<T> {
	List<T> selecionar() throws SQLException;

	void inserir(T entidade) throws SQLException;

	void atualizar(T entidade) throws SQLException;

	void excluir(T entidade) throws SQLException;
}
