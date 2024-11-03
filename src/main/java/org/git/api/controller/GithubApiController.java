package org.git.api.controller;


import org.git.api.model.request.RepositoryRequest;
import org.git.api.model.response.BranchListResponse;
import org.git.api.model.response.CommitsResponse;
import org.git.api.model.response.GithubApiResponse;
import org.git.api.service.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GithubApiController {

    @Autowired
    private GithubApiService service;

    @RequestMapping(method = RequestMethod.GET,path = "/user/repos")
    public List<GithubApiResponse> getRepo(OAuth2AuthenticationToken token) {
        return service.getUserRepos(token);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/user/repos")
    public String createRepo(@RequestBody RepositoryRequest repoRequest, OAuth2AuthenticationToken token) {
        return service.createRepo(repoRequest, token);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/commits/{user}/{repo}")
    public List<CommitsResponse> getCommits(@PathVariable("user") String user, @PathVariable("repo") String repo,
                                            OAuth2AuthenticationToken token) {
        return service.getCommits(user, repo, token);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/repos/{user}/{repo}/branches")
    public List<BranchListResponse> getBranches(@PathVariable("user") String user, @PathVariable("repo") String repo,
                                                OAuth2AuthenticationToken token) {
        return service.getBranchess(user, repo, token);
    }
}
