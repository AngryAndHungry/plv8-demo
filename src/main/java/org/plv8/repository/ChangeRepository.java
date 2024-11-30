package org.plv8.repository;

//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ChangeRepository {
    private final static String GET_SCHEMA_QUERY = "SELECT * FROM plv8.openapi(?, ?, ?::jsonb) as openapi";
    private final static String CHANGE_QUERY = "SELECT * FROM plv8.sql_change(?, ?::jsonb, ?, ?, ?, ?::jsonb) as change";
    private final static String AUDIT_INSERT = "INSERT INTO %S.%s " +
            "(table_name, operation, account_id, made, query, result) " +
            "VALUES(?, ?, ?, ?, ?::jsonb, ?::jsonb)";

    private final JdbcTemplate jdbcTemplate;

    public ChangeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String change(String tableName, String data, String[] ids, String operation, String schema) {
        return jdbcTemplate.query(CHANGE_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString("change");
            } else {
                throw new IllegalStateException("CHANGE_QUERY should return a result");
            }
        }, tableName, data, ids, operation, schema, null);
    }

    public String getSchema(String baseUrl, String schema) {
        return jdbcTemplate.query(GET_SCHEMA_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString("openapi");
            } else {
                return null;
            }
        }, baseUrl, schema, null);
    }

    public void insertAudit(String auditSchema, String auditTable, String tableName, String operation, int accountId,
                            Timestamp timestamp, String dataJson, String resultJson) {
        String sql = String.format(AUDIT_INSERT, auditSchema, auditTable);
        jdbcTemplate.update(sql, tableName, operation, accountId, timestamp, dataJson, resultJson);
    }
}
