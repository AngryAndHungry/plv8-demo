package org.plv8.to;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequest {

    private JsonNode data;

    private String tableName;

    private String[] idKeys;
}
