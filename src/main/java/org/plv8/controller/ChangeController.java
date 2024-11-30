package org.plv8.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.plv8.service.ChangeOperation;
import org.plv8.service.ChangeService;
import org.plv8.to.ChangeRequest;
import org.plv8.to.ChangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeController {

    private final ChangeService changeServices;
    private final String getDefaultBaseSchema;

    public ChangeController(ChangeService changeServices, @Value("{defaultSchema: change}") String schema) {
        this.changeServices = changeServices;
        this.getDefaultBaseSchema = schema;
    }


    @PostMapping(value = "/change")
    @ApiOperation(value = "Insert a json to a table")
    public ChangeResponse insert(@RequestBody ChangeRequest changeRequest) throws JsonProcessingException {
        return change(changeRequest, ChangeOperation.INSERT);
    }

    @PutMapping("/change")
    @ApiOperation(value = "Update or insert a table with a json")
    public ChangeResponse upsert(@RequestBody ChangeRequest changeRequest) throws JsonProcessingException {
        return change(changeRequest, ChangeOperation.UPDATE);
    }

    @DeleteMapping("/change")
    @ApiOperation(value = "Remove json from a table")
    public ChangeResponse remove(@RequestBody ChangeRequest changeRequest) throws JsonProcessingException {
        return change(changeRequest, ChangeOperation.DELETE);
    }

    private ChangeResponse change(ChangeRequest changeRequest, ChangeOperation operation) throws JsonProcessingException {
        return changeServices.change(changeRequest.getTableName(), changeRequest.getData(), changeRequest.getIdKeys(), operation);
    }

    @GetMapping("/getSchema")
    @ApiOperation(value = "Get current schema")
    @ResponseBody
    public String getSchema() {
        return changeServices.getSchema(getDefaultBaseSchema);
    }
}
