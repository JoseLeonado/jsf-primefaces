package br.com.jlcb.util.service.impl;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class SimpleJdbcCallServiceImpl extends SimpleJdbcCall implements Serializable {
	private static final long serialVersionUID = 1L;

	public SimpleJdbcCallServiceImpl(DataSource dataSource) {
		super(dataSource);
	}
}
