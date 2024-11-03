package org.git.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryRequest {

    private String name;
    private String description;
    private String homepage;
    @JsonProperty("private")
    private boolean privateRepo;
    @JsonProperty("is_template")
    private boolean isTemplate;

}
