package br.com.jlcb.util.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jlcb.util.service.CrudService;
import br.com.jlcb.utils.HibernateUtil;

/**
 * @author USUARIO
 *
 * @param <T>
 */
@Service
@Transactional
public class CurdServiceImpl<T> implements CrudService<T> {

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Autowired
	private JdbcTemplateServiceImpl jdbcTemplate;

	@Autowired
	private SimpleJdbcTemplateServiceImpl simpleJdbcTemplate;

	@Autowired
	private SimpleJdbcInsertServiceImpl simpleJdbcInsert;

	@Autowired
	private SimpleJdbcCallServiceImpl simpleJdbcClass;

	@Override
	public void save(T objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().save(objeto);
		executeFlushSession();
	}

	@Override
	public void persist(T objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().persist(objeto);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(T objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(objeto);
		executeFlushSession();
	}

	@Override
	public void update(T objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().update(objeto);
		executeFlushSession();
	}

	@Override
	public void delete(T objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().delete(objeto);
		executeFlushSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T objeto) throws Exception {

		validarSessionFactory();
		objeto = (T) sessionFactory.getCurrentSession().merge(objeto);
		executeFlushSession();

		return objeto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findList(Class<T> entidade) throws Exception {

		validarSessionFactory();

		StringBuilder query = new StringBuilder();
		query.append(" SELECT DISTINCT(e) FROM ").append(entidade.getSimpleName()).append(" e ");

		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		
		validarSessionFactory();

		Object objeto = sessionFactory.getCurrentSession().load(getClass(), id) ;
		
		return objeto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByPorId(Class<T> classe, Long id) throws Exception {
		
		validarSessionFactory();

		T objeto = (T) sessionFactory.getCurrentSession().load(getClass(), id) ;
		
		return objeto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListQueryDinamica(String query) throws Exception {
		
		validarSessionFactory();

		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(query).list();
		
		return lista;
	}

	@Override
	public void executeUpdateQueryDinamica(String query) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDinamica(String sql) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void evict(Object objeto) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().evict(objeto);
	}

	@Override
	public Session getSession() throws Exception {
		
		validarSessionFactory();
		
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {
		
		validarSessionFactory();
		
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		
		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}

	@Override
	public SimpleJdbcCall getSimpleJdbcCall() {
		return simpleJdbcClass;
	}

	@Override
	public Long totalRegistro(String tabela) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(1) FROM ").append(tabela);
		
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuey(String query) throws Exception {
		
		validarSessionFactory();
		
		Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());
		
		return queryReturn;
	}

	/**
	 * Realiza consulta no banco de dados, iniciando o carregamento a partir do
	 * registro passado no paramentro -> iniciaNoRegistro e obtendo maximo de
	 * resultados passados em -> maximoResultado.
	 * 
	 * @param query
	 * @param iniciaNoRegistro
	 * @param maximoResultado
	 * @return List<T>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {
		
		validarSessionFactory();
		
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
		
		return lista;
	}

	private void validarSessionFactory() {

		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}

		validarTransaction();
	}

	private void validarTransaction() {
		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	private void commitProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollbackProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}

	private void executeFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
		
		validarSessionFactory();
		
		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		
		return lista;
	}

}
