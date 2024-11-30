package org.plv8.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GraphQLRepository {

    //    private final static String INSTROSPECTION_QUERY = "SELECT graphql.introspection(@schema, @idField, @idPostfix, @aggPostfix);";
    private final static String INSTROSPECTION_QUERY = "SELECT * FROM graphql.introspection(?, ?, ?, ?) as intersectionResult;";
    private final static String EXECUTE_QUERY = "SELECT * FROM graphql.execute(?, ?, ?::jsonb, ?::jsonb) as execResult;";

    private final JdbcTemplate jdbcTemplate;

    public GraphQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String executeIntrospectionQuery(String schema, String idField, String idPostfix, String aggPostfix) {
        return jdbcTemplate.query(INSTROSPECTION_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString("intersectionResult");
            } else {
                throw new IllegalStateException("INSTROSPECTION_QUERY should return a result");
            }
        }, schema, idField, idPostfix, aggPostfix);
    }

    public String executeQuery(String query, String schema, String variables) {
        return jdbcTemplate.query(EXECUTE_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString("execResult");
            } else {
                throw new IllegalStateException("EXECUTE_QUERY should return a result");
            }
        }, query, schema, null, variables);
    }

}
