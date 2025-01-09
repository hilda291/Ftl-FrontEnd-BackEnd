package com.example.ftl_project.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class DatabaseService {

	private final JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(getClass().getName());

	public DatabaseService(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> fetchMasterRefIdList() {
		String sql = "SELECT SUBSTR(GWY.ERR_TXT, INSTR(GWY.ERR_TXT, 'Master', -1, 1) + 7, 16) AS MASTER_REF "
				+ "FROM GWYIN GWY " + "WHERE THEIR_REF = 'TR-001001' AND GWY.MESS_TYPE = 'TFCPCCRT'";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("MASTER_REF"));
		} catch (Exception e) {
			logger.severe("Error processing template: " + e.getMessage());
			return Collections.emptyList();
		}
	}
}