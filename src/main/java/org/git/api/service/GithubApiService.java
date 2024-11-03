package org.git.api.service;



import org.apache.commons.lang3.StringUtils;
import org.git.api.model.request.RepositoryRequest;
import org.git.api.model.response.BranchListResponse;
import org.git.api.model.response.CommitsResponse;
import org.git.api.model.response.GithubApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.git.api.utils.WebClientUtils.getWebClient;


@Service
public class GithubApiService {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private GitTokenService tokenService;

    public List<GithubApiResponse> getUserRepos(OAuth2AuthenticationToken token) {
        String accessToken = getAccessToken(token);
        return getWebClient("https://api.github.com")
                .get()
                .uri("/user/repos")
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToFlux(GithubApiResponse.class)
                .collectList()
                .block();
    }

    private String getAccessToken(OAuth2AuthenticationToken token) {
        String accessToken = tokenService.getGithubAccessToken();
        if(StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("github", token.getName());
        accessToken = client.getAccessToken().getTokenValue();
        return accessToken;
    }

    public String createRepo(RepositoryRequest repoRequest, OAuth2AuthenticationToken token) {
        String accessToken = getAccessToken(token);
        return getWebClient("https://api.github.com")
                .post()
                .uri("/user/repos")
                .body(repoRequest, RepositoryRequest.class)
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<CommitsResponse> getCommits(String user, String repo, OAuth2AuthenticationToken token) {
        String accessToken = getAccessToken(token);
        return getWebClient("https://api.github.com")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/repos/{user}/{repo}/commits")
                        .build(user, repo))
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToFlux(CommitsResponse.class)
                .collectList()
                .block();
    }

    public List<BranchListResponse> getBranchess(String user, String repo, OAuth2AuthenticationToken token) {
        String accessToken = getAccessToken(token);
        return getWebClient("https://api.github.com")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/repos/{user}/{repo}/branches")
                        .build(user, repo))
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToFlux(BranchListResponse.class)
                .collectList()
                .block();
    }
}
