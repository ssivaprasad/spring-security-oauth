package com.ssp.apps.spring.security.stateless;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Component
@AllArgsConstructor
public class OAuthController {

    public static final String OAUTH_COOKIE_NAME = "OAUTH";

    private final OAuth2AuthorizedClientService  oAuth2AuthorizedClientService;

    @SneakyThrows
    public void oauthSuccessResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getOAuth2AuthorizedClient();
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateCookie("myAuthToken", oAuth2AuthorizedClient.getAccessToken().getTokenValue(), Duration.ofMinutes(30)));
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendRedirect("http://localhost:8111/home");
    }

    private OAuth2AuthorizedClient getOAuth2AuthorizedClient() {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        return authorizedClient;
    }

    @SneakyThrows
    public void oauthFailureResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.getWriter().write("{ \"status\": \"failure\" }");
    }

}
