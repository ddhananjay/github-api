package org.git.api.service;

import org.git.api.model.response.AppDetails;
import org.git.api.model.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.git.api.utils.JwtUtils.getGitJWTTokenFromFile;
import static org.git.api.utils.WebClientUtils.getWebClient;

@Service
public class GitTokenService {

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String clientId;
    public String getGithubAccessToken() {
        String gitJwt = getGitJWTTokenFromFile(clientId);
        AppDetails appDetails = getAppDetails(gitJwt).stream().filter(app ->app.getClientId().equalsIgnoreCase(clientId)).findFirst().orElse(null);
        if(appDetails != null) {
            return getGithubAccessToken(appDetails.getId(), gitJwt).getToken();
        } else {
            throw new RuntimeException("No App found");
        }
    }

    private List<AppDetails> getAppDetails(String gitJwt) {
        return getWebClient("https://api.github.com")
                .get()
                .uri("/app/installations")
                .header("Authorization", "Bearer "+gitJwt)
                .retrieve()
                .bodyToFlux(AppDetails.class)
                .collectList()
                .block();
    }

    private TokenResponse getGithubAccessToken(String appId, String jwtGit) {

        return getWebClient("https://api.github.com")
                .post()
                .uri(uriBuilder -> uriBuilder.path("app/installations/{appId}/access_tokens")
                        .build(appId))
                .header("Authorization", "Bearer "+jwtGit)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
