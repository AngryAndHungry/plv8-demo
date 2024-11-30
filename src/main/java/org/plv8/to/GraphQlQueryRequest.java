package org.plv8.to;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@Data
public class GraphQlQueryRequest {

    private String operationName;

    private HashMap<String, JsonNode> variables = new HashMap<>();

    private String query;
}
