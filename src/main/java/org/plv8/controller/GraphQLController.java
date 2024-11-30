package org.plv8.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.plv8.service.GraphQLService;
import org.plv8.to.GraphQlQueryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphQLController {

    private final GraphQLService graphQLService;

    public GraphQLController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostMapping(value = "/graphql")
    @ResponseBody
    @ApiOperation(value = "Graph QL endpoint")
    public String graphql(
            @RequestBody GraphQlQueryRequest request
    ) throws JsonProcessingException {
        return graphQLService.query(request);
    }
}
