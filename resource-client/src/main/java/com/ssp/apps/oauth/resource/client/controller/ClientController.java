package com.ssp.apps.oauth.resource.client.controller;

import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.ssp.apps.oauth.resource.client.dto.AccessTokenModel;
import com.ssp.apps.oauth.resource.client.dto.AuthorizationCodeModel;

@Controller
public class ClientController {

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/authrizationcode")
    public String authrizationcode(ModelMap model) {
        AuthorizationCodeModel authorizationModel = new AuthorizationCodeModel("code", "client_1",
                "READ", "http://localhost:8080/home");
        model.addAttribute("model", authorizationModel);
        return "authrizationcode";
    }

    @GetMapping("/accesstoken")
    public String acesstoken(ModelMap model) {
        AccessTokenModel accesTokenModel = new AccessTokenModel("", "authorization_code",
                "http://localhost:8080/home", "client_1", "pass");
        model.addAttribute("model", accesTokenModel);
        return "acesstoken";
    }

    @PostMapping("/accesstoken")
    public String getacesstoken(ModelMap modelMap, AccessTokenModel model) {
        String access_token_url = String.format(
                "http://localhost:9191/uaa/oauth/token?code=%s&&grant_type=%s&&redirect_uri=%s",
                model.getCode(), model.getGrant_type(), model.getRedirect_uri());

        String credentials = String.format("%s:%s", model.getClient_Id(), model.getClient_secret());
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(access_token_url, HttpMethod.POST,
                new HttpEntity<String>(headers), String.class);

        System.out.println("== >> Access Token Response : " + response.getBody());

        return "home";
    }

}
