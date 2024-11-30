package org.plv8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plv8AuditSettings {

    private Boolean enabled;
    private String schema;
    private String tableName;
}
