package org.plv8.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.plv8.Plv8Settings;
import org.plv8.repository.ChangeRepository;
import org.plv8.to.ChangeResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ChangeService {

    private final ChangeRepository changeRepository;
    private final Plv8Settings plv8Settings;
    private final ObjectMapper objectMapper;

    public ChangeService(ChangeRepository changeRepository, Plv8Settings plv8Settings, ObjectMapper objectMapper) {
        this.changeRepository = changeRepository;
        this.plv8Settings = plv8Settings;
        this.objectMapper = objectMapper;
    }

    @Nullable
    public ChangeResponse change(String table, JsonNode data, String[] idKeys, ChangeOperation changeOperation) throws JsonProcessingException {
        String dataAsString = objectMapper.writeValueAsString(data);
        String resultJson = changeRepository.change(table, dataAsString, idKeys, changeOperation.getDescription(), plv8Settings.getSchema());

        JsonNode jsonTree = objectMapper.readTree(resultJson);
        JsonNode jsonNode = jsonTree.get(0);
        if (jsonNode == null) {
            return new ChangeResponse(false, "Result is null", jsonTree);
        }

        boolean dataIsChanged = (jsonNode.isInt() && jsonNode.asInt() > 0) || !jsonNode.isInt();
        if (plv8Settings.getAudit().getEnabled() && dataIsChanged) {
            changeRepository.insertAudit(plv8Settings.getAudit().getSchema(), plv8Settings.getAudit().getTableName(),
                    table, changeOperation.toString(), 0, new Timestamp(new Date().getTime()), dataAsString, resultJson);
        }

        if (!jsonNode.isInt()) {
            return new ChangeResponse(dataIsChanged, null, jsonTree);
        }
        return new ChangeResponse(dataIsChanged, "Updated rows amount: " + jsonNode.asText(), jsonTree);
    }

    public String getSchema(String baseUrl) {
        return changeRepository.getSchema(baseUrl, plv8Settings.getSchema());
    }
}
