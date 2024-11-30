package org.plv8.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.plv8.Plv8Settings;
import org.plv8.repository.GraphQLRepository;
import org.plv8.to.GraphQlQueryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class GraphQLService {

    private final static String INTROSPECTION_OPERATION = "IntrospectionQuery";
    private final static String INTROSPECTION_DATA_KEY = "__IntrospectionData";//

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final GraphQLRepository graphQLRepository;
    private final Plv8Settings plv8Settings;
    private final ObjectMapper objectMapper;

    public GraphQLService(GraphQLRepository graphQLRepository, Plv8Settings plv8Settings, ObjectMapper objectMapper) {
        this.graphQLRepository = graphQLRepository;
        this.plv8Settings = plv8Settings;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public String query(GraphQlQueryRequest request) throws JsonProcessingException {
        String queryResult;
        if (INTROSPECTION_OPERATION.equalsIgnoreCase(request.getOperationName())) {
            String cachedJson = cache.getOrDefault(INTROSPECTION_DATA_KEY, null);
            if (cachedJson != null) {
                return cachedJson;
            }

            queryResult = graphQLRepository.executeIntrospectionQuery(plv8Settings.getSchema(), plv8Settings.getIdField(),
                    plv8Settings.getIdPostfix(), plv8Settings.getAggPostfix());
            cache.put(INTROSPECTION_DATA_KEY, queryResult);
        }

        String variablesJsonString = objectMapper.writeValueAsString(request.getVariables());
        queryResult = graphQLRepository.executeQuery(request.getQuery(), plv8Settings.getSchema(), variablesJsonString);
        return queryResult;
    }
}
