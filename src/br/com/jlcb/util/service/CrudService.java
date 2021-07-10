package br.com.jlcb.util.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface CrudService<T> extends Serializable {

	/**
	 * Salva os dados
	 * @return void
	 * @throws Exception
	 */
	void save(T objeto) throws Exception; 
	
	void persist(T objeto) throws Exception; 
	
	/**
	 * Salva ou atualiza os dados
	 * @return void
	 * @throws Exception
	 */
	void saveOrUpdate(T objeto) throws Exception; 
	
	/**
	 * Atualiza os dados
	 * @return void
	 * @throws Exception
	 */
	void update(T objeto) throws Exception; 
	
	/**
	 * Deleta os dados
	 * @return void
	 * @throws Exception
	 */
	void delete(T objeto) throws Exception; 
	
	/**
	 * Salva ou atualiza e retorna o objeto
	 * @return void
	 * @throws Exception
	 */
	T merge(T objeto) throws Exception; 
	
	/**
	 * Carrega a lista de dados de uma determinada classe
	 * @return List<T>
	 * @throws Exception
	 */
	List<T> findList(Class<T> classe) throws Exception; 
	
	Object findById(Class<T> classe, Long id) throws Exception; 
	
	T findByPorId(Class<T> classe, Long id) throws Exception; 
	
	List<T> findListQueryDinamica(String query) throws Exception; 
	
	/**
	 * Executar update com HQL
	 * @return void
	 * @throws Exception
	 */
	void executeUpdateQueryDinamica(String query) throws Exception; 
	
	void executeUpdateSQLDinamica(String sql) throws Exception; 
	
	/**
	 * Limpa a sessão do hibernate
	 * @return void
	 * @throws Exception
	 */
	void clearSession() throws Exception; 
	
	/**
	 * Retira um objeto da sessão do hibernate
	 * @return void
	 * @throws Exception
	 */
	void evict (Object t) throws Exception; 
	
	Session getSession() throws Exception; 
	
	List<?> getListSQLDinamica(String sql) throws Exception; 
	
	/**
	 * JDBC do 	Spring
	 * @return JdbcTemplate
	 * @throws Exception
	 */
	JdbcTemplate getJdbcTemplate();
	
	SimpleJdbcTemplate getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	Long totalRegistro(String table) throws Exception; 
	
	Query obterQuey(String query) throws Exception;
	
	/**
	 * Carregamento dinamico com JSF e PrimeFaces
	 * @return List<T>
	 * @throws Exception
	 */
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
}

