package org.git.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    @JsonProperty("expires_at")
    private String expiry;
    @JsonProperty("repository_selection")
    private String repositoryAccess;
}
