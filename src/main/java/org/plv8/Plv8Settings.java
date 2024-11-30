package org.plv8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@ConfigurationProperties(value = "plv8.settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class Plv8Settings {

    private String idField;
    private String idPostfix;
    private String aggPostfix;
    private String schema;
    private List<String> defaultKeys;
    private Plv8AuditSettings audit;
}
