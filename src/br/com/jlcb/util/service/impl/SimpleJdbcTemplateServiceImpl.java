package br.com.jlcb.util.service.impl;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SimpleJdbcTemplateServiceImpl extends SimpleJdbcTemplate implements Serializable{

	private static final long serialVersionUID = 1L;

	public SimpleJdbcTemplateServiceImpl(DataSource dataSource) {
		super(dataSource);
	}
}
