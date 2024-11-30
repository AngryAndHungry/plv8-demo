package org.plv8.to;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@ApiModel(value = "Change Operation Response")
public class ChangeResponse {

    @ApiModelProperty(required = true)
    private boolean changed;

    @ApiModelProperty
    @Nullable
    private String message;

    @ApiModelProperty
    @Nullable
    private JsonNode data;
}
